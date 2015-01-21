package minium.box;

import io.platypus.MixinInitializer;
import io.platypus.MixinInitializers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import minium.ElementsFactory;
import minium.box.internal.DefaultBoxElementsFactory;

import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;


public interface BoxElementsFactory<T extends BoxElements> extends ElementsFactory {

    public static class Builder<T extends BoxElements> extends ElementsFactory.Builder<BoxElementsFactory<T>> {

        private WebDriver webDriver;
        private ObjectMapper mapper = new ObjectMapper();
        private ClassLoader classLoader = Builder.class.getClassLoader();
        private Set<String> jsResources = Sets.newLinkedHashSet();
        private Set<String> cssResources = Sets.newLinkedHashSet();
        private Set<Class<?>> intfs = Sets.newLinkedHashSet();
        private Set<MixinInitializer> mixinInitializers = Sets.newLinkedHashSet();
        private boolean jsSupport = true;

        public Builder<T> withWebDriver(WebDriver webDriver) {
            this.webDriver = webDriver;
            return this;
        }

        public Builder<T> withObjectMapper(ObjectMapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public Builder<T> withClassLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public Builder<T> withJsSupport(boolean jsSupport) {
            this.jsSupport = jsSupport;
            return this;
        }

        public Builder<T> withJsResources(String ... resources) {
            return this.withJsResources(Arrays.asList(resources));
        }

        public Builder<T> withJsResources(Collection<String> resources) {
            this.jsResources.addAll(resources);
            return this;
        }

        public Builder<T> withCssResources(Collection<String> resources) {
            this.cssResources.addAll(resources);
            return this;
        }

        public Builder<T> implementingInterfaces(Class<?> ... intfs) {
            return this.implementingInterfaces(Arrays.asList(intfs));
        }

        public Builder<T> implementingInterfaces(Collection<Class<?>> intfs) {
            this.intfs.addAll(intfs);
            return this;
        }

        public Builder<T> usingMixinConfigurer(MixinInitializer configurer) {
            this.mixinInitializers.add(configurer);
            return this;
        }


        public WebDriver getWebDriver() {
            return webDriver;
        }

        public ObjectMapper getMapper() {
            return mapper;
        }

        public ClassLoader getClassLoader() {
            return classLoader;
        }

        public Set<String> getJsResources() {
            return ImmutableSet.copyOf(jsResources);
        }

        public Set<String> getCssResources() {
            return ImmutableSet.copyOf(cssResources);
        }

        public Set<Class<?>> getIntfs() {
            return ImmutableSet.copyOf(intfs);
        }

        public MixinInitializer getMixinInitializer() {
            return MixinInitializers.combine(mixinInitializers);
        }

        public boolean isJsSupport() {
            return jsSupport;
        }

        @Override
        public BoxElementsFactory<T> build() {
            return new DefaultBoxElementsFactory<T>(this);
        }
    }

}
