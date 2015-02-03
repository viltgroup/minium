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
import java.util.Collections;
import java.util.List;

import minium.cucumber.config.CucumberProperties;
import minium.cucumber.rest.RemoteBackend;
import minium.script.rhinojs.RhinoEngine;
import minium.script.rhinojs.RhinoProperties;
import minium.script.rhinojs.RhinoProperties.RequireProperties;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;

import cucumber.api.CucumberOptions;
import cucumber.runtime.Backend;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.junit.Assertions;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;

/**
 * Classes annotated with {@code @RunWith(Cucumber.class)} will run a Cucumber Feature.
 * The class should be empty without any fields or methods.
 * <p/>
 * Cucumber will look for a {@code .feature} file on the classpath, using the same resource
 * path as the annotated class ({@code .class} substituted by {@code .feature}).
 * <p/>
 * Additional hints can be given to Cucumber by annotating the class with {@link CucumberOptions}.
 *
 * @see CucumberOptions
 */
public class MiniumCucumber extends ParentRunner<FeatureRunner> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiniumCucumber.class);

    private JUnitReporter jUnitReporter;
    private List<FeatureRunner> children = new ArrayList<FeatureRunner>();
    private Runtime runtime;
    private ClassLoader classLoader;

    @Autowired
    private JsVariablePostProcessor variablePostProcessor;

    private List<Backend> allBackends;

    private RuntimeOptions runtimeOptions;

    public MiniumCucumber(Class<?> clazz) throws InitializationError, IOException {
        this(clazz, null);
    }

    @SuppressWarnings("unchecked")
    public MiniumCucumber(Class<?> clazz, AutowireCapableBeanFactory beanFactory) throws InitializationError, IOException {
        super(clazz);
        classLoader = clazz.getClassLoader();
        Assertions.assertNoCucumberAnnotatedMethods(clazz);

        Object testInstance = null;

        if (beanFactory == null) {
            MiniumRhinoTestContextManager contextManager = new MiniumRhinoTestContextManager(clazz);
            beanFactory = contextManager.getBeanFactory();
            testInstance = contextManager.newInstance();
        } else {
            testInstance = newInstance(clazz);
            initializeInstance(clazz, beanFactory, testInstance);
        }

        initializeInstance(MiniumCucumber.class, beanFactory, this);
        ResourceLoader resourceLoader = new MultiLoader(classLoader);

        CucumberProperties properties = beanFactory.getBean(CucumberProperties.class);
        List<String> args = properties.getOptions().toArgs();

        List<RemoteBackend> remoteBackends;
        if (beanFactory.containsBean("remoteBackends")) {
            remoteBackends = (List<RemoteBackend>) beanFactory.getBean("remoteBackends");
        } else {
            remoteBackends = Collections.emptyList();
        }

        LOGGER.debug("Found cucumber options {}", args);
        LOGGER.debug("Found {} remote backends", remoteBackends.size());

        RequireProperties require = new RequireProperties();
        RhinoProperties rhinoProperties = new RhinoProperties();
        rhinoProperties.setRequire(require);

        RhinoEngine engine = new RhinoEngine(rhinoProperties);
        variablePostProcessor.populateEngine(beanFactory, engine);

        MiniumBackend backend = new MiniumBackend(resourceLoader, engine.getContext(), engine.getScope());
        allBackends = ImmutableList.<Backend>builder().add(backend).addAll(remoteBackends).build();

        RuntimeBuilder runtimeBuilder = new RuntimeBuilder();
        runtime = runtimeBuilder
                .withArgs(args)
                .withClassLoader(classLoader)
                .withResourceLoader(resourceLoader)
                .withBackends(allBackends)
                .build();

        runtimeOptions = runtimeBuilder.getRuntimeOptions();
        jUnitReporter = new JUnitReporter(runtimeOptions.reporter(classLoader), runtimeOptions.formatter(classLoader), runtimeOptions.isStrict());
        addChildren(runtimeOptions.cucumberFeatures(resourceLoader));
    }

    public List<Backend> getAllBackends() {
        return allBackends;
    }

    public RuntimeOptions getRuntimeOptions() {
        return runtimeOptions;
    }

    private Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw Throwables.propagate(e);
        } catch (IllegalAccessException e) {
            throw Throwables.propagate(e);
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
    public void run(RunNotifier notifier) {
        super.run(notifier);
        jUnitReporter.done();
        jUnitReporter.close();
        runtime.printSummary();
    }

    private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
        for (CucumberFeature cucumberFeature : cucumberFeatures) {
            children.add(new FeatureRunner(cucumberFeature, runtime, jUnitReporter));
        }
    }
}
