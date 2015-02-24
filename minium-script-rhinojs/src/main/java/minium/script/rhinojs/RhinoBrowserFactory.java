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
import org.openqa.selenium.remote.CapabilityType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

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
            WebDriver wd = webDriverFactory.create(webDriverProperties);
            WebModule webModule = combine(defaultModule(wd), WebModules.debugModule(), RhinoWebModules.rhinoModule());
            return new WebDriverBrowser<DefaultWebElements>(wd, DefaultWebElements.class, webModule);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
