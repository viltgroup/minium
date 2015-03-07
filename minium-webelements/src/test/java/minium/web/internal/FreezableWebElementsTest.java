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
package minium.web.internal;

import static org.junit.Assert.assertThat;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.Browser;
import minium.web.actions.WebDriverBrowser;
import minium.web.internal.drivers.MockWebDriver;
import minium.web.internal.drivers.MockWebElement;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class FreezableWebElementsTest {

    private MockWebDriver webDriver;
    private Browser<DefaultWebElements> browser;
    private DefaultWebElements root;

    @Before
    public void setup() {
        webDriver = new MockWebDriver();
        browser = new WebDriverBrowser<>(webDriver, DefaultWebElements.class);
        root = browser.root();
    }

    @Test
    public void testFreezeFirstEmptyThenNot() {
        DefaultWebElements textFld = root.find(":text").freeze();

        // first we don't return anything
        webDriver.when(root.find(":text")).thenReturn();
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(0));

        // now we return 1 and so it will freeze...
        webDriver.when(root.find(":text")).thenReturn(new MockWebElement());
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(1));

        // now if we return 2 native elements, textFld will return only one
        webDriver.when(root.find(":text")).thenReturn(new MockWebElement(), new MockWebElement());
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(1));
    }

    @Test
    public void testFreezeFirstNotEmptyThenEmpty() {
        DefaultWebElements textFld = root.find(":text").freeze();

        // first we return 1 element
        webDriver.when(root.find(":text")).thenReturn(new MockWebElement());
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(1));

        // now we return none
        webDriver.when(root.find(":text")).thenReturn();
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(1));
    }
}
