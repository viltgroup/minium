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

import static minium.web.internal.WebModules.combine;
import static minium.web.internal.WebModules.defaultModule;

import java.io.IOException;

import minium.script.js.JsBrowserFactory;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.WebDriverBrowser;
import minium.web.config.WebDriverFactory;
import minium.web.config.WebDriverProperties;
import minium.web.internal.WebModule;
import minium.web.internal.WebModules;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.ObjectMapper;
import minium.internal.Throwables;

public class RhinoBrowserFactory implements JsBrowserFactory {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RhinoEngine engine;
    private final WebDriverFactory webDriverFactory;

    public RhinoBrowserFactory(RhinoEngine engine) {
        this(engine, new WebDriverFactory(null));
    }

    public RhinoBrowserFactory(RhinoEngine engine, WebDriverFactory webDriverFactory) {
        this.engine = engine;
        this.webDriverFactory = webDriverFactory;
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsWebDriverFactory#create(java.lang.Object)
     */
    @Override
    public WebDriverBrowser<?> create(Object obj) {
        try {
            WebDriverProperties webDriverProperties;
            if (obj instanceof Scriptable && "String".equals(((Scriptable) obj).getClassName())) {
                obj = obj.toString();
            }

            if (obj instanceof String) {
                webDriverProperties = new WebDriverProperties();
                webDriverProperties.getDesiredCapabilities().put("browserName", obj);
            } else if (obj instanceof Scriptable) {
                final Scriptable scriptable = (Scriptable) obj;
                String json = engine.runWithContext(engine.new RhinoCallable<String, RuntimeException>() {
                    @Override
                    protected String doCall(Context cx, Scriptable scope) throws RuntimeException {
                        return (String) cx.evaluateString(scriptable, "JSON.stringify(this)", "<expression>", 1, null);
                    }
                });
                webDriverProperties = mapper.readValue(json, WebDriverProperties.class);
            } else {
                throw new IllegalArgumentException("Only strings or maps are accepted");
            }
            WebDriver wd = webDriverFactory.create(webDriverProperties);
            WebModule webModule = combine(defaultModule(wd), WebModules.debugModule(), RhinoWebModules.rhinoModule());
            return new WebDriverBrowser<DefaultWebElements>(wd, DefaultWebElements.class, webModule);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
