package minium.web.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import minium.BasicElements;
import minium.Elements;
import minium.FreezableElements;
import minium.IterableElements;
import minium.internal.DefaultIterableElements;
import minium.internal.HasElementsFactory;
import minium.internal.HasParent;
import minium.internal.InternalElementsFactory;
import minium.web.DocumentWebDriver;
import minium.web.TargetLocatorWebElements;
import minium.web.WebElements;
import minium.web.internal.drivers.DefaultJavascriptInvoker;
import minium.web.internal.drivers.DocumentWebElement;
import minium.web.internal.drivers.InternalDocumentWebDriver;
import minium.web.internal.drivers.JavascriptInvoker;
import minium.web.internal.drivers.WindowWebDriver;
import minium.web.internal.expression.Coercer;
import minium.web.internal.expression.ExpressionWebElementExpressionizer;
import minium.web.internal.expression.Expressionizer;
import minium.web.internal.expression.IdentityCoercer;
import minium.web.internal.expression.JsonCoercer;
import minium.web.internal.expression.JsonExpressionizer;
import minium.web.internal.expression.PrimitiveTypeCoercer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import platypus.AbstractMixinInitializer;
import platypus.Mixin;
import platypus.MixinClass;
import platypus.MixinClasses;
import platypus.MixinInitializer;
import platypus.MixinInitializers;
import platypus.internal.Casts;

import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;

public class DefaultWebElementsFactory<T extends WebElements> extends Mixin.Impl implements WebElementsFactory<T>, InternalElementsFactory<T> {

    private static final Class<?>[] CORE_INTFS = new Class<?>[] {
        Elements.class,
        InternalWebElements.class,
        HasElementsFactory.class,
        HasNativeWebDriver.class,
        HasExpressionizer.class,
        HasCoercer.class,
        ExpressionWebElements.class,
        TargetLocatorWebElements.class,
        FreezableElements.class,
        IterableElements.class
    };

    private Set<Class<?>> builerProvidedInterfaces;
    @SuppressWarnings("serial")
    private final TypeToken<T> typeVariableToken = new TypeToken<T>(getClass()) {};
    private final InternalDocumentWebDriver rootDocumentDriver;
    private final MixinClass<T> rootClass;
    private final MixinClass<T> hasParentClass;
    private final MixinInitializer baseInitializer;


    public DefaultWebElementsFactory(final Builder<T> builder) {

        WebDriver wd = Preconditions.checkNotNull(builder.getWebDriver());

        final JavascriptInvoker javascriptInvoker = new DefaultJavascriptInvoker(builder.getClassLoader(), builder.getJsResources(), builder.getCssResources());
        final Expressionizer expressionizer = new Expressionizer.Composite()
            .add(new JsonExpressionizer(builder.getMapper()))
            .add(new ExpressionWebElementExpressionizer())
            .addAll(builder.getAditionalExpressionizers());
        final Coercer coercer = new Coercer.Composite()
            .add(new JsonCoercer(builder.getMapper()))
            .add(new PrimitiveTypeCoercer())
            .add(new IdentityCoercer())
            .addAll(builder.getAditionalCoercers());

        this.rootDocumentDriver = wd instanceof InternalDocumentWebDriver ? ((InternalDocumentWebDriver) wd) : new WindowWebDriver(wd);

        Class<T> intf = Casts.unsafeCast(typeVariableToken.getRawType());

        builerProvidedInterfaces = builder.getIntfs();
        MixinClasses.Builder<T> mixinBuilder = MixinClasses.builder(intf).addInterfaces(CORE_INTFS).addInterfaces(builerProvidedInterfaces);

        mixinBuilder.addInterfaces(HasJavascriptInvoker.class);

        rootClass = mixinBuilder.build();
        hasParentClass = mixinBuilder.addInterfaces(HasParent.class).build();

        baseInitializer = MixinInitializers.combine(builder.getMixinInitializer(), new AbstractMixinInitializer() {
            @SuppressWarnings("rawtypes")
            @Override
            protected void initialize() {
                implement(HasElementsFactory.class).with(new HasElementsFactory.Impl(DefaultWebElementsFactory.this));
                implement(HasNativeWebDriver.class).with(new HasNativeWebDriver.Impl(rootDocumentDriver.nativeWebDriver()));
                implement(HasExpressionizer.class).with(new HasExpressionizer.Impl(expressionizer));
                implement(HasCoercer.class).with(new HasCoercer.Impl(coercer));
                implement(TargetLocatorWebElements.class).with(new DefaultTargetLocatorWebElements());
                implement(IterableElements.class).with(new DefaultIterableElements());
                implement(HasJavascriptInvoker.class).with(new HasJavascriptInvoker.Impl(javascriptInvoker));

                // dynamic invocation handlers
                ExpressionInvocationHandler<T> expressionInvocationHandler = new ExpressionInvocationHandler<T>(DefaultWebElementsFactory.this, coercer);
                for (Class<?> intf : builerProvidedInterfaces) {
                    implement(intf).with(expressionInvocationHandler);
                }
            }
        });

    }

    @Override
    public Set<Class<?>> getProvidedInterfaces() {
        return ImmutableSet.copyOf(builerProvidedInterfaces);
    }

    @Override
    public T createNative(DocumentWebDriver webDriver, WebElement ... nativeWebElements) {
        return createNative(webDriver, Arrays.asList(nativeWebElements));
    }

    @Override
    public T createNative(DocumentWebDriver webDriver, Collection<WebElement> nativeWebElements) {
        return createNative(FluentIterable.from(nativeWebElements).transform(WebElementFunctions.wrap(webDriver)).toList());
    }

    @Override
    public T createNative(DocumentWebElement ... nativeWebElements) {
        return createNative(Arrays.asList(nativeWebElements));
    }

    @Override
    public T createNative(final Collection<DocumentWebElement> nativeWebElements) {
        return createMixin(new NativeWebElements<T>(nativeWebElements));
    }

    @Override
    public T createRoot() {
        return createMixin(new DefaultRoot<T>(rootDocumentDriver));
    }

    /* (non-Javadoc)
     * @see minium.web.internal.InternalWebElementsFactory#createMixin(minium.webElements)
     */
    @Override
    public T createMixin(final Elements elems) {
        AbstractMixinInitializer initializer = new AbstractMixinInitializer() {
            @Override
            protected void initialize() {
                implement(Object.class).with(elems);
                implement(InternalWebElements.class).with(elems);
                implement(FreezableElements.class).with(elems);
                implement(ExpressionWebElements.class).with(elems);
                // this way, we can use an optimized implementation for frozen and native WebElements
                if (elems instanceof BasicElements) {
                    implement(BasicElements.class).with(elems);
                }
            }
        };
        return rootClass.newInstance(MixinInitializers.combine(initializer, baseInitializer));
    }

    /* (non-Javadoc)
     * @see minium.web.internal.InternalWebElementsFactory#createMixinWithParent(minium.webElements, minium.webElements)
     */
    @Override
    public T createMixin(final Elements parent, final Elements elems) {
        AbstractMixinInitializer initializer = new AbstractMixinInitializer() {
            @Override
            protected void initialize() {
                implement(Object.class).with(elems);
                implement(HasParent.class).with(new HasParent.Impl(parent));
                implement(InternalWebElements.class).with(elems);
                implement(FreezableElements.class).with(elems);
                implement(ExpressionWebElements.class).with(elems);
                // this way, we can use an optimized implementation for frozen and native WebElements
                if (elems instanceof BasicElements) {
                    implement(BasicElements.class).with(elems);
                }
            }
        };
        return hasParentClass.newInstance(MixinInitializers.combine(initializer, baseInitializer));
    }
}