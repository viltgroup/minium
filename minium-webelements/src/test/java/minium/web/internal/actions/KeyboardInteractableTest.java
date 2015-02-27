package minium.web.internal.actions;

import static org.mockito.Mockito.verify;
import minium.actions.Keys;

import org.junit.Test;

public class KeyboardInteractableTest extends BaseInteractableTest {

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
}
