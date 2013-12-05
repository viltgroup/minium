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
package com.vilt.minium.script;

import static java.lang.String.format;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class MiniumContextLoader {

    private static final String RHINO_BOOTSTRAP_JS = "rhino/bootstrap.js";
    private static final String BOOTSTRAP_EXTS_JS = "rhino/bootstrap-extension.js";

    private static final Logger logger = LoggerFactory.getLogger(MiniumContextLoader.class);

    private ClassLoader classLoader;
    private WebElementsDriverFactory webElementsDriverFactory;

    public MiniumContextLoader(ClassLoader classLoader) {
        this(classLoader, null);
    }

    public MiniumContextLoader(ClassLoader classLoader, WebElementsDriverFactory webElementsDriverFactory) {
        this.webElementsDriverFactory = webElementsDriverFactory;
        this.classLoader = classLoader;
    }

    public void load(Context cx, Scriptable scriptable) throws IOException {
        if (webElementsDriverFactory != null) {
            scriptable.put("webElementsDriverFactory", scriptable, Context.toObject(webElementsDriverFactory, scriptable));
        }

        logger.debug("Loading minium bootstrap file");
        URL resourceUrl = classLoader.getResource(RHINO_BOOTSTRAP_JS);
        Preconditions.checkNotNull(resourceUrl);

        evalJS(cx, scriptable, format("load('%s')", resourceUrl.toExternalForm()));

        Enumeration<URL> resources = classLoader.getResources(BOOTSTRAP_EXTS_JS);

        while (resources.hasMoreElements()) {
            URL extResourceUrl = resources.nextElement();
            if (extResourceUrl != null) {
                logger.debug("Loading extension bootstrap from '{}'", extResourceUrl.toString());
                evalJS(cx, scriptable, format("load('%s')", extResourceUrl.toExternalForm()));
            }
        }
    }

    private Object evalJS(Context cx, Scriptable scope, String js) {
        return cx.evaluateString(scope, js, "script", 1, null);
    }
}
