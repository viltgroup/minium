/*
 * Copyright (C) 2013 The Minium Authors
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
package minium.cucumber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import minium.cucumber.config.CucumberProperties;
import minium.cucumber.config.CucumberProperties.RemoteBackendProperties;
import minium.cucumber.internal.MiniumRhinoTestContextManager;
import minium.cucumber.internal.RuntimeBuilder;
import minium.cucumber.rest.RemoteBackend;
import minium.script.js.JsVariablePostProcessor;
import minium.script.rhinojs.RhinoEngine;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cucumber.runtime.Backend;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.junit.Assertions;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;

public class MiniumCucumber extends ParentRunner<FeatureRunner> {

    @SpringApplicationConfiguration(classes = MiniumConfiguration.class)
    static class MiniumCucumberTest { }

    private final JUnitReporter jUnitReporter;
    private final List<FeatureRunner> children = new ArrayList<FeatureRunner>();
    private final Runtime runtime;

    @Autowired
    private CucumberProperties cucumberProperties;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private RhinoEngine rhinoEngine;

    @Autowired
    private JsVariablePostProcessor jsVariablePostProcessor;

    public MiniumCucumber(Class<?> clazz) throws InitializationError, IOException {
        super(clazz);
        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);

        MiniumRhinoTestContextManager contextManager = new MiniumRhinoTestContextManager(MiniumCucumberTest.class);
        ConfigurableListableBeanFactory beanFactory = contextManager.getBeanFactory();

        // this will populate @Autowired fields
        initializeInstance(MiniumCucumber.class, beanFactory, this);

        // now we populate RhinoEngine with minium modules, etc.
        jsVariablePostProcessor.populateEngine(rhinoEngine);

        // we now build cucumber runtime and load glues
        RuntimeBuilder runtimeBuilder = rhinoEngine.runWithContext(rhinoEngine.new RhinoCallable<RuntimeBuilder, IOException>() {
            @Override
            protected RuntimeBuilder doCall(Context cx, Scriptable scope) throws IOException {
                RuntimeBuilder runtimeBuilder = new RuntimeBuilder();
                runtimeBuilder
                    .withArgs(cucumberProperties.getOptions().toArgs())
                    .withClassLoader(Thread.currentThread().getContextClassLoader())
                    .withResourceLoader(resourceLoader)
                    .withBackends(allBackends())
                    .build();
                return runtimeBuilder;
            }
        });
        runtime = runtimeBuilder.getRuntime();
        RuntimeOptions runtimeOptions = runtimeBuilder.getRuntimeOptions();

        final List<CucumberFeature> cucumberFeatures = runtimeOptions.cucumberFeatures(resourceLoader);
        jUnitReporter = new JUnitReporter(runtimeOptions.reporter(classLoader), runtimeOptions.formatter(classLoader), runtimeOptions.isStrict());
        addChildren(cucumberFeatures);
    }

    @Override
    public List<FeatureRunner> getChildren() {
        return children;
    }

    @Override
    protected Description describeChild(FeatureRunner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(FeatureRunner child, RunNotifier notifier) {
        child.run(notifier);
    }

    @Override
    public void run(final RunNotifier notifier) {
        rhinoEngine.runWithContext(rhinoEngine.new RhinoCallable<Void, RuntimeException>() {
            @Override
            protected Void doCall(Context cx, Scriptable scope) {
                doRun(notifier);
                return null;
            }
        });
        jUnitReporter.done();
        jUnitReporter.close();
        runtime.printSummary();
    }

    private void doRun(RunNotifier notifier) {
        super.run(notifier);
    }

    private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
        for (CucumberFeature cucumberFeature : cucumberFeatures) {
            children.add(new FeatureRunner(cucumberFeature, runtime, jUnitReporter));
        }
    }

    private void initializeInstance(Class<?> clazz, AutowireCapableBeanFactory beanFactory, Object testInstance) {
        try {
            beanFactory.autowireBeanProperties(testInstance, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
            beanFactory.initializeBean(testInstance, clazz.getName());
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    protected List<Backend> allBackends() throws IOException {
        ArrayList<RemoteBackend> remoteBackends = Lists.newArrayList();
        for (RemoteBackendProperties remoteBackendProperties : cucumberProperties.getRemoteBackends()) {
            remoteBackends.add(remoteBackendProperties.createRemoteBackend());
        }
        Backend miniumBackend = rhinoEngine.runWithContext(rhinoEngine.new RhinoCallable<Backend, IOException>() {
            @Override
            protected Backend doCall(Context cx, Scriptable scope) throws IOException {
                return new MiniumBackend(resourceLoader, cx, scope);
            }
        });
        return ImmutableList.<Backend>builder().add(miniumBackend).addAll(remoteBackends).build();
    }
}
