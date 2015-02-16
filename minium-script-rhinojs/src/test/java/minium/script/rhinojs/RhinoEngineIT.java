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
import minium.web.WebLocator;
import minium.web.internal.WebElementsFactory;
import minium.web.internal.WebModule;
import minium.web.internal.WebElementsFactory.Builder;
import minium.web.internal.actions.WebInteractionPerformer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class RhinoEngineIT {

    private static ChromeDriver wd;
    private static WebLocator<DefaultWebElements> by;

    @BeforeClass
    public static void setup() {
        wd = new ChromeDriver();
        Builder<DefaultWebElements> builder = new WebElementsFactory.Builder<>();
        InteractionPerformer performer = new WebInteractionPerformer();
        WebModule module = combine(
                baseModule(wd),
                positionModule(),
                conditionalModule(),
                rhinoModule(),
                interactableModule(performer));
        module.configure(builder);
        DefaultWebElements root = builder.build().createRoot();
        by = new WebLocator<>(root, DefaultWebElements.class, JsFunctionWebElements.class);
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
        new MiniumJsEngineAdapter(by, null).adapt(engine);
        engine.runScript("classpath:minium/script/rhinojs/gs.js");
    }
}
