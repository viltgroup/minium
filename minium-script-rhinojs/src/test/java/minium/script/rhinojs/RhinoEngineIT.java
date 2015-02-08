package minium.script.rhinojs;

import static minium.script.rhinojs.RhinoWebModules.rhinoModule;
import static minium.web.WebModules.baseModule;
import static minium.web.WebModules.combine;
import static minium.web.WebModules.conditionalModule;
import static minium.web.WebModules.interactableModule;
import static minium.web.WebModules.positionModule;

import java.io.IOException;

import minium.actions.InteractionPerformer;
import minium.script.js.MiniumJsEngineAdapter;
import minium.script.rhinojs.CoreRhinoJsWebElements.DefaultRhinoJsWebElements;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.WebElementsFactory;
import minium.web.WebElementsFactory.Builder;
import minium.web.WebFinder;
import minium.web.WebModule;
import minium.web.internal.actions.WebInteractionPerformer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class RhinoEngineIT {

    private static ChromeDriver wd;
    private static WebFinder<DefaultRhinoJsWebElements> by;

    @BeforeClass
    public static void setup() {
        wd = new ChromeDriver();
        Builder<DefaultRhinoJsWebElements> builder = new WebElementsFactory.Builder<>();
        InteractionPerformer performer = new WebInteractionPerformer();
        WebModule module = combine(
                baseModule(wd, DefaultRhinoJsWebElements.class),
                positionModule(),
                conditionalModule(),
                rhinoModule(),
                interactableModule(performer));
        module.configure(builder);
        DefaultRhinoJsWebElements root = builder.build().createRoot();
        by = new WebFinder<>(root, DefaultRhinoJsWebElements.class);
    }

    @AfterClass
    public static void tearDown() {
        by.release();
        wd.quit();
    }

    @Test
    public void test_rhino_eval() throws IOException, InterruptedException {
        RhinoProperties properties = new RhinoProperties();
        RequireProperties requireProperties = new RequireProperties();
        properties.setRequire(requireProperties);
        final RhinoEngine engine = new RhinoEngine(properties);
        new MiniumJsEngineAdapter(by).adapt(engine);
        engine.runScript("classpath:minium/script/rhinojs/gs.js");
    }
}
