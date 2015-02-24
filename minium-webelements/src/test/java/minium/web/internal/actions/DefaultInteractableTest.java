package minium.web.internal.actions;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import minium.Offsets;
import minium.Offsets.HorizontalReference;
import minium.Offsets.VerticalReference;
import minium.actions.Keys;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.WebDriverBrowser;
import minium.web.internal.drivers.MockWebDriver;
import minium.web.internal.drivers.MockWebElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

import com.google.common.collect.ImmutableList;

@RunWith(MockitoJUnitRunner.class)
public class DefaultInteractableTest {

    private MockWebDriver mockedWebDriver;
    private MockWebElement mockedWebElement;
    private WebDriverBrowser<DefaultWebElements> browser;
    private DefaultWebElements interactable;
    private Mouse mouse;
    private Keyboard keyboard;

    @Before
    public void setup() {
        mockedWebElement = spy(new MockWebElement());
        mockedWebDriver = spy(new MockWebDriver() {
            @Override
            protected Object doExecuteScript(String script, Object... args) {
                if (script.contains(".size()")) return 1;
                return ImmutableList.of(mockedWebElement);
            }
        });
        browser = new WebDriverBrowser<>(mockedWebDriver, DefaultWebElements.class);

        interactable = browser.locator().root();
        mouse = mockedWebDriver.getMouse();
        keyboard = mockedWebDriver.getKeyboard();
    }

    @Test
    public void testClear() {
        interactable.clear();
        verify(mockedWebElement).clear();
    }

    @Test
    public void testKeyDown() {
        interactable.keyDown(Keys.SHIFT);
        verify(keyboard).pressKey(org.openqa.selenium.Keys.SHIFT);
    }

    @Test
    public void testKeyUp() {
        interactable.keyUp(Keys.SHIFT);
        verify(keyboard).releaseKey(org.openqa.selenium.Keys.SHIFT);
    }

    @Test
    public void testSendKeys() {
        interactable.sendKeys("Minium can!");
        verify(mockedWebElement).sendKeys("Minium can!");
    }

    @Test
    public void testType() {
        interactable.type("Minium can!");
        verify(mockedWebElement).sendKeys("Minium can!");
    }

    @Test
    public void testFill() {
        interactable.fill("Minium can!");
        verify(mockedWebElement).clear();
        verify(mockedWebElement).sendKeys("Minium can!");
    }

    @Test
    public void clickAndHold() {
        interactable.clickAndHold();
        verify(mouse).mouseDown(mockedWebElement.getCoordinates());
    }

    @Test
    public void testClickAndHoldWithOffset() {
        interactable.clickAndHold(Offsets.at(HorizontalReference.RIGHT, VerticalReference.TOP));
        verify(mouse).mouseMove(mockedWebElement.getCoordinates(), mockedWebElement.getSize().width, 0);
        verify(mouse).mouseDown(null);
    }

    @Test
    public void testContextClick() {
        interactable.contextClick();
        verify(mouse).contextClick(mockedWebElement.getCoordinates());
    }

    @Test
    public void testMoveTo() {
        interactable.moveTo();
        verify(mouse).mouseMove(mockedWebElement.getCoordinates());
    }

    @Test
    public void testMoveToWithOffset() {
        interactable.moveTo(Offsets.at(HorizontalReference.RIGHT, VerticalReference.TOP));
        verify(mouse).mouseMove(mockedWebElement.getCoordinates(), mockedWebElement.getSize().width, 0);
    }

    @Test
    public void testDoubleClick() {
        interactable.doubleClick();
        verify(mouse).doubleClick(mockedWebElement.getCoordinates());
    }

    @Test
    public void testClick() {
        interactable.click();
        verify(mouse).click(mockedWebElement.getCoordinates());
    }

    @Test
    public void testRelease() {
        interactable.release();
        verify(mouse).mouseUp(mockedWebElement.getCoordinates());
    }

    @Test
    public void testReleaseWithOffset() {
        interactable.release(Offsets.at(HorizontalReference.RIGHT, VerticalReference.TOP));
        verify(mouse).mouseMove(mockedWebElement.getCoordinates(), mockedWebElement.getSize().width, 0);
        verify(mouse).mouseUp(null);
    }

}
