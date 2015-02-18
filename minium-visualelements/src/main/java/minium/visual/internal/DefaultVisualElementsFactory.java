package minium.visual.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import minium.Elements;
import minium.FreezableElements;
import minium.IterableElements;
import minium.internal.DefaultIterableElements;
import minium.internal.HasElementsFactory;
import minium.internal.HasParent;
import minium.internal.InternalElementsFactory;
import minium.visual.VisualElements;

import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import platypus.AbstractMixinInitializer;
import platypus.Mixin;
import platypus.MixinClass;
import platypus.MixinClasses;
import platypus.MixinInitializer;
import platypus.MixinInitializers;
import platypus.internal.Casts;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.reflect.TypeToken;

public class DefaultVisualElementsFactory<T extends VisualElements> extends Mixin.Impl implements VisualElementsFactory<T>, InternalElementsFactory<T> {

    static class FrozenVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private Iterable<Region> matches;

        @Override
        public Iterable<Region> matches(VisualContext context) {
            if (this.matches == null) {
                Iterable<Region> matches = context.evaluate(parent());
                if (Iterables.isEmpty(matches)) {
                    return Collections.emptySet();
                } else {
                    this.matches = ImmutableSet.copyOf(matches);
                }
            }
            return this.matches;
        }
    }

    private static final Class<?>[] CORE_INTFS = new Class<?>[] {
        Elements.class,
        InternalVisualElements.class,
        HasElementsFactory.class,
        HasScreen.class,
        FreezableElements.class,
        IterableElements.class
    };

    private Set<Class<?>> builderProvidedInterfaces;
    @SuppressWarnings("serial")
    private final TypeToken<T> typeVariableToken = new TypeToken<T>(getClass()) {};
    private final Screen screen;
    private final MixinClass<T> rootClass;
    private final MixinClass<T> hasParentClass;
    private final MixinInitializer baseInitializer;

    public DefaultVisualElementsFactory(Builder<T> builder) {
        Class<T> intf = Casts.unsafeCast(typeVariableToken.getRawType());

        this.screen = builder.getScreen() == null ? new Screen() : builder.getScreen();

        builderProvidedInterfaces = builder.getIntfs();
        MixinClasses.Builder<T> mixinBuilder = MixinClasses.builder(intf).addInterfaces(CORE_INTFS).addInterfaces(builderProvidedInterfaces);

        rootClass = mixinBuilder.build();
        hasParentClass = mixinBuilder.addInterfaces(HasParent.class).build();

        baseInitializer = MixinInitializers.combine(builder.getMixinInitializer(), new AbstractMixinInitializer() {
            @SuppressWarnings("rawtypes")
            @Override
            protected void initialize() {
                implement(HasElementsFactory.class).with(new HasElementsFactory.Impl(DefaultVisualElementsFactory.this));
                implement(HasScreen.class).with(new HasScreen.Impl(DefaultVisualElementsFactory.this.screen));
                implement(IterableElements.class).with(new DefaultIterableElements());
                implement(FreezableElements.class).with(new FreezableVisualElements());
            }
        });
    }

    @Override
    public Set<Class<?>> getProvidedInterfaces() {
        return ImmutableSet.copyOf(builderProvidedInterfaces);
    }

    @Override
    public T createRoot() {
        return createNative(screen);
    }

    @Override
    public T createNative(final Region... regions) {
        return createNative(Arrays.asList(regions));
    }

    @Override
    public T createNative(final Collection<Region> regions) {
        return createMixin(new Root<T>(regions));
    }

    @Override
    public T createNative(final Elements parent) {
        return createMixin(parent, new AdapterImpl<T>());
    }

    @Override
    public T createMixin(final Elements elem) {
        AbstractMixinInitializer initializer = new AbstractMixinInitializer() {
            @Override
            protected void initialize() {
                implement(Object.class).with(elem);
                implement(InternalVisualElements.class).with(elem);
            }
        };
        return rootClass.newInstance(MixinInitializers.combine(initializer, baseInitializer));
    }

    @Override
    public T createMixin(final Elements parent, final Elements elem) {
        Preconditions.checkNotNull(parent);
        AbstractMixinInitializer initializer = new AbstractMixinInitializer() {
            @Override
            protected void initialize() {
                implement(HasParent.class).with(new HasParent.Impl(parent));
                implement(Object.class).with(elem);
                implement(InternalVisualElements.class).with(elem);
            }
        };
        return hasParentClass.newInstance(MixinInitializers.combine(initializer, baseInitializer));
    }
}
