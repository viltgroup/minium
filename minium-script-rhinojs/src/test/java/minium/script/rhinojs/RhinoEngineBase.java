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

import static minium.script.rhinojs.RhinoWebModules.rhinoModule;
import static minium.web.internal.WebModules.baseModule;
import static minium.web.internal.WebModules.combine;
import static minium.web.internal.WebModules.conditionalModule;
import static minium.web.internal.WebModules.interactableModule;
import static minium.web.internal.WebModules.positionModule;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import minium.script.js.MiniumJsEngineAdapter;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.StatefulWebDriver;
import minium.web.actions.Browser;
import minium.web.actions.WebDriverBrowser;
import minium.web.config.WebElementsConfiguration;
import minium.web.internal.WebModule;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RhinoEngineBase.TestConfig.class)
@TestExecutionListeners(SpringBootDependencyInjectionTestExecutionListener.class)
public class RhinoEngineBase {

    @Configuration
    @Import(WebElementsConfiguration.class)
    public static class TestConfig {

        @Autowired
        @Bean(destroyMethod = "quit")
        public Browser<DefaultWebElements> browser(WebDriver wd) {
            WebModule module = combine(
                    baseModule(new StatefulWebDriver(wd)),
                    positionModule(),
                    conditionalModule(),
                    rhinoModule(),
                    interactableModule());
            return new WebDriverBrowser<>(wd, DefaultWebElements.class, module);
        }

        @Autowired
        @Bean
        public RhinoEngine rhinoEngine(Browser<DefaultWebElements> browser) {
            RhinoProperties properties = new RhinoProperties();
            RequireProperties requireProperties = new RequireProperties();
            properties.setRequire(requireProperties);
            RhinoEngine engine = new RhinoEngine(properties);
            new MiniumJsEngineAdapter(browser, null).adapt(engine);
            return engine;
        }
    }

    @Autowired
    private RhinoEngine rhinoEngine;

    @Test
    public void testResponseHeaders() throws IOException, InterruptedException {
        rhinoEngine.runScript("classpath:minium/examples/responseHeaders.js");
    }
}
