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
