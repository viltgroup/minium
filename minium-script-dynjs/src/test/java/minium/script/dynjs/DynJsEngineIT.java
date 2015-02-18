package minium.script.dynjs;

import static minium.script.dynjs.DynJsWebModules.dynJsModule;
import static minium.web.internal.WebModules.baseModule;
import static minium.web.internal.WebModules.combine;
import static minium.web.internal.WebModules.conditionalModule;
import static minium.web.internal.WebModules.interactableModule;
import static minium.web.internal.WebModules.positionModule;

import java.io.IOException;

import minium.actions.internal.InteractionPerformer;
import minium.script.dynjs.DynJsProperties.RequireProperties;
import minium.script.js.MiniumJsEngineAdapter;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.Browser;
import minium.web.actions.WebDriverBrowser;
import minium.web.internal.WebModule;
import minium.web.internal.actions.WebInteractionPerformer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.collect.ImmutableList;

public class DynJsEngineIT {

    private static Browser<?> browser;
    private static DynJsEngine engine;

    @BeforeClass
    public static void setup() {
        DynJsProperties properties = new DynJsProperties();
        RequireProperties requireProperties = new RequireProperties();
        properties.setRequire(requireProperties);
        requireProperties.setModulePaths(ImmutableList.of("classpath:modules"));
        engine = new DynJsEngine(properties);

        ChromeDriver wd = new ChromeDriver();
        InteractionPerformer performer = new WebInteractionPerformer();
        WebModule module = combine(
                baseModule(wd),
                positionModule(),
                conditionalModule(),
                dynJsModule(engine.getExecutionContext()),
                interactableModule(performer));
        browser = new WebDriverBrowser<>(wd, DefaultWebElements.class, module);

        new MiniumJsEngineAdapter(browser, null).adapt(engine);
    }

    @AfterClass
    public static void tearDown() {
        browser.quit();
    }

    @Test
    public void test_dynjs_eval() throws IOException, InterruptedException {
        engine.runScript("classpath:minium/script/dynjs/gs.js");
        Thread.sleep(1000);
    }
}
