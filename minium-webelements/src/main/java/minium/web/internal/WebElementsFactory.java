package minium.web.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import minium.internal.ElementsFactory;
import minium.web.DocumentWebDriver;
import minium.web.WebElements;
import minium.web.internal.drivers.DocumentWebElement;
import minium.web.internal.expression.Coercer;
import minium.web.internal.expression.Expressionizer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import platypus.MixinInitializer;
import platypus.MixinInitializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public interface WebElementsFactory<T extends WebElements> extends ElementsFactory<T> {

    public abstract T createNative(DocumentWebDriver webDriver, WebElement ... nativeWebElements);

    public abstract T createNative(DocumentWebDriver webDriver, Collection<WebElement> nativeWebElements);

    public abstract T createNative(DocumentWebElement ... nativeWebElements);

    public abstract T createNative(Collection<DocumentWebElement> nativeWebElements);

    public static class Builder<T extends WebElements> implements ElementsFactory.Builder<WebElementsFactory<T>> {

        private WebDriver webDriver;
        private ObjectMapper mapper = new ObjectMapper();
        private ClassLoader classLoader = Builder.class.getClassLoader();
        private Set<String> jsResources = Sets.newLinkedHashSet();
        private Set<String> cssResources = Sets.newLinkedHashSet();
        private Set<Expressionizer> expressionizers = Sets.newLinkedHashSet();
        private Set<Coercer> coercers = Sets.newLinkedHashSet();
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

        public Builder<T> withCssResources(String ... resources) {
            return this.withCssResources(Arrays.asList(resources));
        }

        public Builder<T> withCssResources(Collection<String> resources) {
            this.cssResources.addAll(resources);
            return this;
        }

        public Builder<T> withExpressionizers(Expressionizer ... expressionizers) {
            return withExpressionizers(Arrays.asList(expressionizers));
        }

        public Builder<T> withExpressionizers(Collection<Expressionizer> expressionizers) {
            this.expressionizers.addAll(expressionizers);
            return this;
        }

        public Builder<T> withCoercers(Coercer ... coercers) {
            return withCoercers(Arrays.asList(coercers));
        }

        public Builder<T> withCoercers(Collection<Coercer> coercers) {
            this.coercers.addAll(coercers);
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


        public Collection<Expressionizer> getAditionalExpressionizers() {
            return ImmutableSet.copyOf(expressionizers);
        }

        public Collection<Coercer> getAditionalCoercers() {
            return ImmutableSet.copyOf(coercers);
        }

        public MixinInitializer getMixinInitializer() {
            return MixinInitializers.combine(mixinInitializers);
        }

        public boolean isJsSupport() {
            return jsSupport;
        }

        @Override
        public WebElementsFactory<T> build() {
            return new DefaultWebElementsFactory<T>(this);
        }

    }
}