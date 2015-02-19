package minium.web.internal.actions;

import java.util.Collections;

import minium.actions.Interactable;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.WebDriverBrowser;
import minium.web.internal.drivers.MockWebDriver;
import minium.web.internal.drivers.MockWebElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultInteractableTest {

    private MockWebDriver webDriver;

    @Before
    public void setup() {
        webDriver = new MockWebDriver() {
            @Override
            protected Object doExecuteScript(String script, Object... args) {
                return script.contains(".size()") ? 1 : Collections.singletonList(new MockWebElement());
            }
        };
    }

    @Test
    public void testInteractions() {
        // given
        WebDriverBrowser<DefaultWebElements> browser = new WebDriverBrowser<>(webDriver, DefaultWebElements.class);

        Interactable interactable = browser.locator().root().as(Interactable.class);

        // when
//        interactable.scrollIntoView();
//        interactable.clear();
//        interactable.submit();
//        interactable.keyDown(Keys.SHIFT);
//        interactable.keyUp(Keys.SHIFT);
//        interactable.sendKeys("Minium can!");
//        interactable.clickAndHold();
//        interactable.clickAndHold(Offsets.at(HorizontalReference.RIGHT, VerticalReference.TOP));
//        interactable.release();
//        interactable.release(Offsets.at(HorizontalReference.RIGHT, VerticalReference.TOP));
//        interactable.click();
//        interactable.doubleClick();
//        interactable.moveTo();
//        interactable.moveTo(Offsets.at(HorizontalReference.RIGHT, VerticalReference.TOP));
//        interactable.contextClick();
//        interactable.dragAndDrop(otherElem);
//        interactable.clickAll();
//        interactable.type("Minium can!");
//        interactable.fill("Minium can!");
//        interactable.check();
//        interactable.uncheck();
//        interactable.waitUntilClosed();
//        interactable.select("Option 1");
//        interactable.deselect("Option 1");
//        interactable.selectVal("option_1");
//        interactable.deselectVal("option_1");
//        interactable.selectAll();
//        interactable.deselectAll();
    }
}
