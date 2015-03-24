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
/*
 * Copyright (c) 2008-2014 The Cucumber Organisation
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package minium.cucumber.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import minium.cucumber.MiniumConfiguration;
import minium.cucumber.MiniumCucumber;
import minium.cucumber.config.ConfigProperties;
import minium.cucumber.config.CucumberProperties;
import minium.cucumber.config.CucumberProperties.RemoteBackendProperties;
import minium.cucumber.rest.RemoteBackend;
import minium.script.js.JsBrowserFactory;
import minium.script.js.MiniumJsEngineAdapter;
import minium.script.rhinojs.RhinoEngine;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.Browser;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cucumber.runtime.Backend;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;

public class MiniumProfileRunner extends ParentRunner<FeatureRunner>implements InitializingBean {

    @SpringApplicationConfiguration(classes = MiniumConfiguration.class)
    @ActiveProfiles(resolver = MiniumActiveProfilesResolver.class)
    public static class MiniumCucumberTest { }

    private static final Logger LOGGER = LoggerFactory.getLogger(MiniumProfileRunner.class);

    @Autowired
    private Environment environment;

    @Autowired
    private CucumberProperties cucumberProperties;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private RhinoEngine rhinoEngine;

    @Autowired
    @Lazy
    private Browser<DefaultWebElements> browser;

    @Autowired
    private JsBrowserFactory factory;

    @Autowired
    private ConfigProperties configProperties;

    private List<FeatureRunner> children = Lists.newArrayList();
    private JUnitReporter jUnitReporter;
    private Runtime runtime;
    private List<CucumberFeature> cucumberFeatures;
    private MiniumRhinoTestContextManager testContextManager;
    private Description description;

    public MiniumProfileRunner() throws InitializationError {
        super(MiniumCucumberTest.class);
    }

    public void setTestContextManager(MiniumRhinoTestContextManager testContextManager) {
        this.testContextManager = testContextManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassLoader classLoader = MiniumCucumberTest.class.getClassLoader();

        // now we populate RhinoEngine with minium modules, etc.
        new MiniumJsEngineAdapter(browser, factory).adapt(rhinoEngine);

        // and set configuration
        rhinoEngine.putJson("config", configProperties.toJson());

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

        cucumberFeatures = runtimeOptions.cucumberFeatures(resourceLoader);
        jUnitReporter = new JUnitReporter(runtimeOptions.reporter(classLoader), runtimeOptions.formatter(classLoader), runtimeOptions.isStrict());
        addChildren(cucumberFeatures);
    }

    @Override
    public Description getDescription() {
        if (description == null) {
            description = Description.createSuiteDescription(getName());
            for (FeatureRunner child : getChildren()) {
                description.addChild(describeChild(child));
            }
        }
        return description;
    }

    @Override
    public void run(final RunNotifier notifier) {
        try {
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
        } finally {
            try {
                if (testContextManager != null) testContextManager.destroy();
            } catch (Exception e) {
                LOGGER.warn("Failed destroying TestContentManager for profiles {}", environment.getActiveProfiles(), e);
            }
        }
    }

    @Override
    protected String getName() {
        return String.format("%s [%s]", MiniumCucumber.class.getName(), Joiner.on(", ").join(environment.getActiveProfiles()));
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

    @Override
    protected List<FeatureRunner> getChildren() {
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

    private void doRun(RunNotifier notifier) {
        super.run(notifier);
    }

    private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
        for (CucumberFeature cucumberFeature : cucumberFeatures) {
            children.add(new FeatureRunner(cucumberFeature, runtime, jUnitReporter));
        }
    }
}
