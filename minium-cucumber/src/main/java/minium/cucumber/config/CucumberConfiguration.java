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
package minium.cucumber.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import minium.cucumber.MiniumBackend;
import minium.cucumber.config.CucumberProperties.RemoteBackendProperties;
import minium.cucumber.internal.RuntimeBuilder;
import minium.cucumber.rest.RemoteBackend;
import minium.script.rhinojs.RhinoEngine;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cucumber.runtime.Backend;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;

@Configuration
@EnableConfigurationProperties
public class CucumberConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "minium.cucumber", ignoreUnknownFields = false)
    public CucumberProperties cucumberProperties() {
        return new CucumberProperties();
    }

    @Bean
    @Autowired
    public List<RemoteBackend> remoteBackends(CucumberProperties cucumberProperties) {
        ArrayList<RemoteBackend> backends = Lists.newArrayList();
        for (RemoteBackendProperties remoteBackendProperties : cucumberProperties.getRemoteBackends()) {
            backends.add(remoteBackendProperties.createRemoteBackend());
        }
        return backends;
    }

    @Bean
    @Autowired
    public RuntimeBuilder runtimeBuilder(RhinoEngine engine, final ResourceLoader resourceLoader, CucumberProperties cucumberProperties) throws IOException  {
        final List<String> args = cucumberProperties.getOptions().toArgs();
        final List<Backend> backends = allBackends(engine, resourceLoader, cucumberProperties);
        return engine.runWithContext(engine.new RhinoCallable<RuntimeBuilder, RuntimeException>() {

            @Override
            protected RuntimeBuilder doCall(Context cx, Scriptable scope) throws RuntimeException {
                RuntimeBuilder runtimeBuilder = new RuntimeBuilder();
                runtimeBuilder
                    .withArgs(args)
                    .withClassLoader(Thread.currentThread().getContextClassLoader())
                    .withResourceLoader(resourceLoader)
                    .withBackends(backends)
                    .build();
                return runtimeBuilder;
            }
        });
    }

    @Bean
    @Autowired
    public Runtime runtime(RuntimeBuilder runtimeBuilder) {
        return runtimeBuilder.getRuntime();
    }

    @Bean
    @Autowired
    public RuntimeOptions runtimeOptions(RuntimeBuilder runtimeBuilder) {
        return runtimeBuilder.getRuntimeOptions();
    }

    @Bean
    public ResourceLoader resourceLoader() {
        return new MultiLoader(Thread.currentThread().getContextClassLoader());
    }

    protected List<Backend> allBackends(RhinoEngine engine, final ResourceLoader resourceLoader, CucumberProperties cucumberProperties) throws IOException {
        ArrayList<RemoteBackend> remoteBackends = Lists.newArrayList();
        for (RemoteBackendProperties remoteBackendProperties : cucumberProperties.getRemoteBackends()) {
            remoteBackends.add(remoteBackendProperties.createRemoteBackend());
        }
        Backend miniumBackend = engine.runWithContext(engine.new RhinoCallable<Backend, IOException>() {
            @Override
            protected Backend doCall(Context cx, Scriptable scope) throws IOException {
                return new MiniumBackend(resourceLoader, cx, scope);
            }
        });
        return ImmutableList.<Backend>builder().add(miniumBackend).addAll(remoteBackends).build();
    }
}
