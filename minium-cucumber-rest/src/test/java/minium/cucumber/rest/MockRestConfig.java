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
package minium.cucumber.rest;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cucumber.runtime.Backend;

@Configuration
public class MockRestConfig implements BackendConfigurer {

    @Bean(name = "mockedBackend")
    public Backend backendMock() {
        return mock(Backend.class);
    }

    @Override
    public void addBackends(BackendRegistry registry) {
        registry.register("mockedBackend", backendMock());
    }
}
