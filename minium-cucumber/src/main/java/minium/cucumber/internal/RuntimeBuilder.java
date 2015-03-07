/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.cucumber.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import cucumber.runtime.Backend;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.formatter.PluginFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;

public class RuntimeBuilder {

    private Class<?> testClass;
    private ClassLoader classLoader;
    private ResourceLoader resourceLoader;
    private final List<String> featurePaths = Lists.newArrayList();
    private final List<Backend> backends = Lists.newArrayList();
    private final List<Object> plugins = Lists.newArrayList();
    private PluginFactory pluginFactory;
    private RuntimeOptions runtimeOptions;
    private final List<String> args = Lists.newArrayList();
    private Runtime runtime;

    public RuntimeBuilder() {
    }

    public RuntimeBuilder withTestClass(Class<?> testClass) {
        this.testClass = testClass;
        return this;
    }

    public RuntimeBuilder withClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public RuntimeBuilder withResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        return this;
    }

    public RuntimeBuilder withPluginFactory(PluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
        return this;
    }

    public RuntimeBuilder withRuntimeOptions(RuntimeOptions runtimeOptions) {
        this.runtimeOptions = runtimeOptions;
        return this;
    }

    public RuntimeBuilder withBackends(Backend ... backends) {
        return withBackends(Arrays.asList(backends));
    }

    public RuntimeBuilder withBackends(List<Backend> backends) {
        this.backends.addAll(backends);
        return this;
    }

    public RuntimeBuilder withArgs(String ... args) {
        return withArgs(Arrays.asList(args));
    }

    public RuntimeBuilder withArgs(Collection<String> args) {
        this.args.addAll(args);
        return this;
    }

    public RuntimeBuilder withFeaturePaths(String ... args) {
        return withFeaturePaths(Arrays.asList(args));
    }

    public RuntimeBuilder withFeaturePaths(Collection<String> featurePaths) {
        this.featurePaths.addAll(featurePaths);
        return this;
    }

    public RuntimeBuilder withPlugins(Object ... plugins) {
        return withPlugins(Arrays.asList(plugins));
    }

    public RuntimeBuilder withPlugins(Collection<?> plugins) {
        this.plugins.addAll(plugins);
        return this;
    }

    public Runtime build() {
        if (classLoader == null) classLoader = testClass == null ? Thread.currentThread().getContextClassLoader() : testClass.getClassLoader();
        if (resourceLoader == null) resourceLoader = new MultiLoader(classLoader);
        if (pluginFactory == null) pluginFactory = new PluginFactory();
        if (runtimeOptions == null) {
            Preconditions.checkArgument(!args.isEmpty() || testClass != null, "RuntimeOptions not provided, need args or testClass");
            runtimeOptions = args.isEmpty() ? new RuntimeOptionsFactory(testClass).create() : new RuntimeOptions(pluginFactory, args);
        }
        runtimeOptions.getFeaturePaths().addAll(featurePaths);
        for (Object plugin : plugins) {
            runtimeOptions.addPlugin(plugin);
        }
        runtime = new Runtime(resourceLoader, classLoader, backends, runtimeOptions);
        return runtime;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public RuntimeOptions getRuntimeOptions() {
        return runtimeOptions;
    }

    public Runtime getRuntime() {
        return runtime;
    }
}
