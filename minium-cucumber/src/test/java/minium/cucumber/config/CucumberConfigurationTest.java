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

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import minium.cucumber.config.CucumberProperties.CredentialsProperties;
import minium.cucumber.config.CucumberProperties.RemoteBackendProperties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { CucumberConfigurationTest.TestConfiguration.class })
@ActiveProfiles("test")
public class CucumberConfigurationTest {

    @Configuration
    @EnableConfigurationProperties
    public static class TestConfiguration {
        @Bean
        @ConfigurationProperties(prefix = "minium.cucumber", ignoreUnknownFields = false)
        public CucumberProperties cucumberProperties() {
            return new CucumberProperties();
        }
    }

    @Autowired
    private CucumberProperties cucumberProperties;

    @Test
    public void testRemoteBackendConfiguration() {
        assertThat(cucumberProperties.getRemoteBackends(), contains(
                create("http://minium/b1", "b1"),
                create("http://minium/b2", "b2", "admin", "secret")));
    }

    private RemoteBackendProperties create(String url, String backendId) {
        RemoteBackendProperties remoteBackendProperties = new RemoteBackendProperties();
        remoteBackendProperties.setUrl(url);
        return remoteBackendProperties;
    }

    private RemoteBackendProperties create(String url, String backendId, String username, String password) {
        RemoteBackendProperties remoteBackendProperties = create(url, backendId);
        CredentialsProperties credentialsProperties = new CredentialsProperties();
        credentialsProperties.setUsername(username);
        credentialsProperties.setPassword(password);
        remoteBackendProperties.setCredentials(credentialsProperties);
        return remoteBackendProperties;
    }
}
