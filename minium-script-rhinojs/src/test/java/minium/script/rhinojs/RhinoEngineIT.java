package minium.script.rhinojs;

import static minium.script.rhinojs.RhinoWebModules.rhinoModule;
import static minium.web.internal.WebModules.baseModule;
import static minium.web.internal.WebModules.combine;
import static minium.web.internal.WebModules.conditionalModule;
import static minium.web.internal.WebModules.interactableModule;
import static minium.web.internal.WebModules.positionModule;

import java.io.IOException;

import minium.actions.internal.InteractionPerformer;
import minium.script.js.MiniumJsEngineAdapter;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.WebDriverBrowser;
import minium.web.internal.WebModule;
import minium.web.internal.actions.WebInteractionPerformer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class RhinoEngineIT {

    private static WebDriverBrowser<DefaultWebElements> browser;

    @BeforeClass
    public static void setup() {
        ChromeDriver wd = new ChromeDriver();
        InteractionPerformer performer = new WebInteractionPerformer();
        WebModule module = combine(
                baseModule(wd),
                positionModule(),
                conditionalModule(),
                rhinoModule(),
                interactableModule(performer));
        browser = new WebDriverBrowser<>(wd, DefaultWebElements.class, module);
    }

    @AfterClass
    public static void tearDown() {
        browser.quit();
    }

    @Test
    public void test_rhino_eval() throws IOException, InterruptedException {
        RhinoProperties properties = new RhinoProperties();
        RequireProperties requireProperties = new RequireProperties();
        properties.setRequire(requireProperties);
        final RhinoEngine engine = new RhinoEngine(properties);
        new MiniumJsEngineAdapter(browser, null).adapt(engine);
        engine.runScript("classpath:minium/script/rhinojs/gs.js");
    }
}
