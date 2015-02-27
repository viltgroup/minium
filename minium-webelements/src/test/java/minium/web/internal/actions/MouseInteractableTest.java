package minium.web.internal.actions;

import static org.mockito.Mockito.verify;
import minium.Offsets;
import minium.Offsets.HorizontalReference;
import minium.Offsets.VerticalReference;

import org.junit.Test;

public class MouseInteractableTest extends BaseInteractableTest {

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
