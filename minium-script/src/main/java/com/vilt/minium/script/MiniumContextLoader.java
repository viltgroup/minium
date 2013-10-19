package com.vilt.minium.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiniumContextLoader {
    
    private static final String RHINO_BOOTSTRAP_JS = "rhino/bootstrap.js";
    private static final String BOOTSTRAP_EXTS_JS = "rhino/bootstrap-extension.js";

    private static final Logger logger = LoggerFactory.getLogger(MiniumContextLoader.class);

    private ClassLoader classLoader;
    private WebElementsDriverFactory webElementsDriverFactory;

    public MiniumContextLoader(WebElementsDriverFactory webElementsDriverFactory, ClassLoader classLoader) {
        this.webElementsDriverFactory = webElementsDriverFactory;
        this.classLoader = classLoader;
    }

    public void load(Context cx, Scriptable scriptable) throws IOException {
        if (webElementsDriverFactory != null) {
            scriptable.put("webElementsDriverFactory", scriptable, Context.toObject(webElementsDriverFactory, scriptable));
        }

        logger.debug("Loading minium bootstrap file");
        InputStreamReader bootstrap = new InputStreamReader(classLoader.getResourceAsStream(RHINO_BOOTSTRAP_JS), "UTF-8");
        cx.evaluateReader(scriptable, bootstrap, RHINO_BOOTSTRAP_JS, 1, null);

        Enumeration<URL> resources = classLoader.getResources(BOOTSTRAP_EXTS_JS);

        while (resources.hasMoreElements()) {
            URL resourceUrl = resources.nextElement();
            Reader reader = resourceUrlReader(resourceUrl);
            if (reader != null) {
                logger.debug("Loading extension bootstrap from '{}'", resourceUrl.toString());
                cx.evaluateReader(scriptable, reader, resourceUrl.toString(), 1, null);
            }
        }
    }
    
    private Reader resourceUrlReader(URL resourceUrl) {
        try {
            InputStream is = resourceUrl.openStream();
            return new BufferedReader(new InputStreamReader(is));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
