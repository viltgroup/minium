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
package minium.script.rhinojs;

import java.io.IOException;

import minium.script.js.JsVariablePostProcessor;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.config.WebDriverFactory;
import minium.web.internal.WebModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RhinoProperties.class)
public class RhinoConfiguration {

    @Bean
    public JsVariablePostProcessor jsVariablePostProcessor() {
        return new JsVariablePostProcessor();
    }

    @Bean
    public WebModule rhinoWebModule() {
        return RhinoWebModules.rhinoModule();
    }

    @Bean
    @ConfigurationProperties(prefix = "minium.rhino")
    public RhinoProperties rhinoProperties() {
        RequireProperties require = new RequireProperties();
        RhinoProperties rhinoProperties = new RhinoProperties();
        rhinoProperties.setRequire(require);
        return rhinoProperties;
    }

    @Autowired(required = false)
    @Bean
    public RhinoEngine rhinoEngine(RhinoProperties rhinoProperties) throws IOException {
        return new RhinoEngine(rhinoProperties);
    }

    @Autowired
    @Bean
    public RhinoBrowserFactory rhinoBrowserFactory(RhinoEngine engine, WebDriverFactory webDriverFactory) {
        return new RhinoBrowserFactory(engine, webDriverFactory);
    }
}
