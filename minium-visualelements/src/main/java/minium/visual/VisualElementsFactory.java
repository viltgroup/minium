package minium.visual;

import io.platypus.MixinInitializer;
import io.platypus.MixinInitializers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import minium.Elements;
import minium.ElementsFactory;
import minium.visual.internal.DefaultVisualElementsFactory;

import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import com.google.common.collect.Sets;

public interface VisualElementsFactory<T extends VisualElements> extends ElementsFactory {

    public T createRoot(Region ... regions);

    public T createRoot(Collection<Region> regions);

    public T createAdapter(Elements parent);

    public static class Builder<T extends VisualElements> extends ElementsFactory.Builder<VisualElementsFactory<T>> {

        private Set<Class<?>> intfs = Sets.newLinkedHashSet();
        private Screen screen;
        private Set<MixinInitializer> mixinInitializers = Sets.newLinkedHashSet();

        public Builder<T> implementingInterfaces(Class<?> ... intfs) {
            this.intfs.addAll(Arrays.asList(intfs));
            return this;
        }

        public Builder<T> usingScreen(Screen screen) {
            this.screen = screen;
            return this;
        }

        public Builder<T> usingMixinConfigurer(MixinInitializer mixinInitializer) {
            this.mixinInitializers.add(mixinInitializer);
            return this;
        }

        public Set<Class<?>> getIntfs() {
            return intfs;
        }

        public MixinInitializer getMixinInitializer() {
            return MixinInitializers.combine(mixinInitializers);
        }

        public Screen getScreen() {
            return screen;
        }

        @Override
        public VisualElementsFactory<T> build() {
            return new DefaultVisualElementsFactory<T>(this);
        }
    }
}
