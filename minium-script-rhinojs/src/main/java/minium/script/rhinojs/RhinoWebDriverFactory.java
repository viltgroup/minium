package minium.script.rhinojs;

import java.io.IOException;

import minium.script.js.JsWebDriverFactory;
import minium.web.config.WebDriverFactory;
import minium.web.config.WebDriverProperties;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

public class RhinoWebDriverFactory implements JsWebDriverFactory {

    private final ObjectMapper mapper;
    private final RhinoEngine engine;
    private final WebDriverFactory webDriverFactory;

    public RhinoWebDriverFactory(RhinoEngine engine) {
        this(new ObjectMapper(), engine, new WebDriverFactory(null));
    }

    public RhinoWebDriverFactory(ObjectMapper mapper, RhinoEngine engine, WebDriverFactory webDriverFactory) {
        this.mapper = mapper;
        this.engine = engine;
        this.webDriverFactory = webDriverFactory;
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsWebDriverFactory#create(java.lang.Object)
     */
    @Override
    public WebDriver create(Object obj) {
        try {
            WebDriverProperties webDriverProperties;
            if (obj instanceof Scriptable && "String".equals(((Scriptable) obj).getClassName())) {
                obj = obj.toString();
            }

            if (obj instanceof String) {
                webDriverProperties = new WebDriverProperties();
                webDriverProperties.getDesiredCapabilities().put(CapabilityType.BROWSER_NAME, obj);
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
            return webDriverFactory.create(webDriverProperties);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
