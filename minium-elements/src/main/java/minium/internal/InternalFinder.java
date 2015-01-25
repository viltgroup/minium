package minium.internal;

import io.platypus.AbstractMixinInitializer;
import io.platypus.InstanceProviders;
import io.platypus.Mixin;
import io.platypus.MixinClass;
import io.platypus.MixinClasses;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

import minium.Elements;
import minium.Minium;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.reflect.TypeToken;

/**
 * Elements classes used in finder chain calls MUST NOT use reified generics. The only exception is
 * {@link Elements#as(Class)}.
 *
 * For instance, the following code is not supported:
 *
 * <pre><code>
 * public interface TestElements implements Elements {
 *   <T extends Elements> T test();
 * }
 *
 * // this will throw an Exception
 * TestElements testElems = Finder.by(TestElements.class).selector(":text").test();
 * </code></pre>
 *
 * Basically, those restriction is due to type erasure, and Mockito suffers from the same problem
 * (http://stackoverflow.com/questions/15906220/classcastexception-with-generics-and-mockito).
 *
 * @author rui.figueira
 */
public interface InternalFinder {

    public Elements eval(Elements elems);

    public abstract Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

    static class ElementsProxy extends Mixin.Impl implements Elements {

        @Override
        public boolean is(Class<?> clazz) {
            return Elements.class.isAssignableFrom(clazz);
        }

        @Override
        public boolean is(TypeToken<?> type) {
            throw new UnsupportedOperationException("Finders don't support type tokens, please use Elements#is(Class<?>) instead");
        }

        @Override
        public <T> T as(Class<T> clazz) {
            return createElementsProxy(clazz);
        }

        @Override
        public <T> T as(TypeToken<T> type) {
            throw new UnsupportedOperationException("Finders don't support type tokens, please use Elements#as(Class<?>) instead");
        }

        protected <T> T createElementsProxy(final Class<T> intf) {
            InternalFinder thisFinder = super.as(InternalFinder.class);
            return InternalFinder.MethodInvocationImpl.createInternalFinder(intf, thisFinder);
        }
    }

    static class Impl extends ElementsProxy implements InternalFinder, InvocationHandler {

        protected final TypeToken<?> typeToken;
        protected final InternalFinder parent;

        public Impl(Class<?> intf, InternalFinder parent) {
            this.typeToken = TypeToken.of(intf);
            this.parent = parent;
        }

        @Override
        public Elements eval(Elements elems) {
            return elems;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            final Class<?> returnIntf = typeToken.resolveType(method.getGenericReturnType()).getRawType();
            if (!Elements.class.isAssignableFrom(returnIntf)) {
                // let's check if Minium has some root set
                Elements root = Minium.get();
                Preconditions.checkState(root != null, "Method %s does not return an Elements class and Minium.get() does not return any root Elements", method);

                Elements elements = eval(root);
                return method.invoke(elements, args);
            }
            return createInternalFinder(returnIntf, this, method, args);
        }

        public static <T> T createInternalFinder(final Class<T> intf, InternalFinder parent) {
            MixinClass<T> returnMixinClass = MixinClasses.create(intf, InternalFinder.class);
            final InternalFinder.Impl internalFinder = new InternalFinder.Impl(intf, parent);
            return returnMixinClass.newInstance(new AbstractMixinInitializer() {
                @Override
                protected void initialize() {
                    implement(Elements.class).with(new ElementsProxy());
                    implement(InternalFinder.class).with(InstanceProviders.ofInstance(internalFinder));
                    implement(intf).with(InstanceProviders.adapt(internalFinder, intf));
                }
            });
        }

        public static <T> T createInternalFinder(final Class<T> intf, InternalFinder parent, Method method, Object ... args) {
            MixinClass<T> returnMixinClass = MixinClasses.create(intf, InternalFinder.class);
            final InternalFinder.MethodInvocationImpl internalFinder = new InternalFinder.MethodInvocationImpl(intf, parent, method, args);
            return returnMixinClass.newInstance(new AbstractMixinInitializer() {
                @Override
                protected void initialize() {
                    implement(Elements.class).with(new ElementsProxy());
                    implement(InternalFinder.class).with(InstanceProviders.ofInstance(internalFinder));
                    implement(intf).with(InstanceProviders.adapt(internalFinder, intf));
                }
            });
        }
    }

    static class MethodInvocationImpl extends Impl implements InternalFinder {

        private final WeakHashMap<Elements, Elements> evalResults = new WeakHashMap<Elements, Elements>();

        private final Method method;
        private final Object[] args;

        public MethodInvocationImpl(Class<?> intf, InternalFinder parent, Method method, Object[] args) {
            super(intf, parent);
            this.method = method;
            this.args = args;
        }

        @Override
        public Elements eval(Elements root) {
            if (evalResults.containsKey(root)) {
                return evalResults.get(root);
            }
            Elements elems = root;
            if (parent != null) {
                elems = parent.eval(root);
            }
            try {
                Elements result = (Elements) method.invoke(elems, evalArgs(root, args));
                evalResults.put(root, result);
                return result;
            } catch (InvocationTargetException e) {
                throw Throwables.propagate(e.getTargetException());
            } catch (IllegalAccessException | IllegalArgumentException e) {
                throw Throwables.propagate(e);
            }
        }

        private Object[] evalArgs(Elements root, Object[] args) {
            if (args == null) return null;
            Object[] evalArgs = new Object[args.length];

            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof InternalFinder) {
                    arg = ((InternalFinder) arg).eval(root);
                }
                evalArgs[i] = arg;
            }
            return evalArgs;
        }
    }
}
