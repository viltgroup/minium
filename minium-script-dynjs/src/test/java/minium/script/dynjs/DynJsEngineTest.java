package minium.script.dynjs;

import static minium.script.dynjs.DynJsWebModules.dynJsModule;
import static minium.web.WebModules.baseModule;
import static minium.web.WebModules.combine;
import static minium.web.WebModules.conditionalModule;
import static minium.web.WebModules.interactableModule;
import static minium.web.WebModules.positionModule;

import java.io.IOException;

import minium.Minium;
import minium.actions.InteractionPerformer;
import minium.script.dynjs.CoreDynJsWebElements.DefaultDynJsWebElements;
import minium.script.dynjs.DynJsProperties.RequireProperties;
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

public class DynJsEngineTest {

    private static WebDriver wd;
    private static DynJsEngine engine;

    @BeforeClass
    public static void setup() {
        DynJsProperties properties = new DynJsProperties();
        RequireProperties requireProperties = new RequireProperties();
        properties.setRequire(requireProperties);
        requireProperties.setModulePaths(ImmutableList.of("classpath:modules"));
        engine = new DynJsEngine(properties);

        wd = new ChromeDriver();
        Builder<DefaultDynJsWebElements> builder = new WebElementsFactory.Builder<DefaultDynJsWebElements>()
                .implementingInterfaces(DefaultDynJsWebElements.class);
        InteractionPerformer performer = new WebInteractionPerformer();
        WebModule module = combine(
                baseModule(wd),
                positionModule(),
                conditionalModule(),
                dynJsModule(engine.getExecutionContext()),
                interactableModule(performer));
        module.configure(builder);
        DefaultDynJsWebElements root = builder.build().createRoot();
        Minium.set(root);
        engine.put("wd", wd);
    }

    @AfterClass
    public static void tearDown() {
        Minium.release();
        wd.quit();
    }

    @Test
    public void test_dynjs_eval() throws IOException, InterruptedException {
        engine.runScript("classpath:minium/script/dynjs/gs.js");
        Thread.sleep(1000);
    }
}
