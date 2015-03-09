/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        verify(mockedWebElement).click();
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
