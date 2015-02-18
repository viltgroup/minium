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

import java.util.ArrayList;
import java.util.List;

import minium.cucumber.config.CucumberProperties.RemoteBackendProperties;
import minium.cucumber.rest.RemoteBackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

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
    public ResourceLoader resourceLoader() {
        return new MultiLoader(Thread.currentThread().getContextClassLoader());
    }

}
