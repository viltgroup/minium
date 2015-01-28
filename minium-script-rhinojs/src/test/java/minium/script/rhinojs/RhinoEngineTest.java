package minium.script.rhinojs;

import static minium.script.rhinojs.RhinoWebModules.rhinoModule;
import static minium.web.WebModules.baseModule;
import static minium.web.WebModules.combine;
import static minium.web.WebModules.conditionalModule;
import static minium.web.WebModules.interactableModule;
import static minium.web.WebModules.positionModule;

import java.io.IOException;

import minium.Minium;
import minium.actions.InteractionPerformer;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.WebElementsFactory;
import minium.web.WebElementsFactory.Builder;
import minium.web.WebModule;
import minium.web.internal.actions.WebInteractionPerformer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.collect.ImmutableList;

public class RhinoEngineTest {

    private static WebDriver wd;

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
        Minium.set(root);
    }

    @AfterClass
    public static void tearDown() {
        Minium.release();
        wd.quit();
    }

    @Test
    public void test_rhino_eval() throws IOException, InterruptedException {
        RhinoProperties properties = new RhinoProperties();
        RequireProperties requireProperties = new RequireProperties();
        properties.setRequire(requireProperties);
        requireProperties.setModulePaths(ImmutableList.of("classpath:modules"));

        RhinoEngine engine = new RhinoEngine(properties);
        engine.put("wd", wd);
        engine.runScript("classpath:minium/script/rhinojs/gs.js");

        Thread.sleep(1000);
    }
}
