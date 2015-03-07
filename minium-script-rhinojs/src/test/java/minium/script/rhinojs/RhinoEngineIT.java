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

import minium.script.js.MiniumJsEngineAdapter;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.WebDriverBrowser;
import minium.web.internal.WebModule;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class RhinoEngineIT {

    private static WebDriverBrowser<DefaultWebElements> browser;

    @BeforeClass
    public static void setup() {
        ChromeDriver wd = new ChromeDriver();
        WebModule module = combine(
                baseModule(wd),
                positionModule(),
                conditionalModule(),
                rhinoModule(),
                interactableModule());
        browser = new WebDriverBrowser<>(wd, DefaultWebElements.class, module);
    }

    @AfterClass
    public static void tearDown() {
        browser.quit();
    }

    @Test
    public void testRhinoEval() throws IOException, InterruptedException {
        RhinoProperties properties = new RhinoProperties();
        RequireProperties requireProperties = new RequireProperties();
        properties.setRequire(requireProperties);
        final RhinoEngine engine = new RhinoEngine(properties);
        new MiniumJsEngineAdapter(browser, null).adapt(engine);
        engine.runScript("classpath:minium/script/rhinojs/gs.js");
    }
}
