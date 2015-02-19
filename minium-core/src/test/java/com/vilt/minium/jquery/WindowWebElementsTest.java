/*
 * Copyright (C) 2013 The Minium Authors
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
package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.actions.Interactions.checkEmpty;
import static com.vilt.minium.actions.Interactions.click;
import static com.vilt.minium.actions.Interactions.waitWhileEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;
import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.WebElementsException;

public class WindowWebElementsTest extends MiniumBaseTest {

    @BeforeMethod
    public void openPage() {
        get("minium/tests/window-test.html");
    }

    @AfterMethod
    public void closeWindows() {
        DefaultWebElements windows = $(wd).windows();
        // windows that do not have a h2 tag with text "Windows Tests"
        DefaultWebElements popupWindows = windows.not(windows.has(windows.find("h2").withText("Window Tests"))).root();

        popupWindows.close();
    }

    @Test
    public void testWindows() {
        DefaultWebElements elems = $(wd).windows().find("input#name");
        waitWhileEmpty(elems);
        assertEquals(1, Iterables.size(elems));
    }

    @Test
    public void testWindowsWithFrames() {
        DefaultWebElements elems = $(wd).windows().frames().find("input#name");
        assertEquals(2, Iterables.size(elems));
    }

    @Test
    public void testWindowsWithObjectResult() {
        DefaultWebElements input = $(wd).windows().find("input#name");
        String html = input.html();
        assertThat(html, notNullValue());
    }

    @Test
    public void testFrozenWindows() {
        DefaultWebElements frozenWindow = $(wd).windows().find("h2").withText("Iframe Tests").root().freeze();
        String h2Text = frozenWindow.find("h2").text();
        assertEquals(h2Text , "Iframe Tests");

        click($(wd, "#change-popup"));

        h2Text = frozenWindow.find("h2").text();
        assertEquals(h2Text , "Position Tests");
    }

    @Test
    public void testNotFrozenWindows() {
        DefaultWebElements notFrozenWindow = $(wd).windows().find("h2").withText("Iframe Tests").root();
        String h2Text = notFrozenWindow.find("h2").text();
        assertEquals(h2Text , "Iframe Tests");

        click($(wd, "#change-popup"));

        checkEmpty(notFrozenWindow.find("h2"));
    }

    @Test
    public void testWindowsWithObjectResultFailed() {
        try {
            DefaultWebElements input = $(wd).windows().find("h1");

            /* String html = */ input.html();

            Assert.fail("An exception was expected");
        } catch (WebElementsException e) {
            // ok
        }
    }
}
