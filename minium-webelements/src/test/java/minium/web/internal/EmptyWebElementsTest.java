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

public class EmptyWebElementsTest {

    private MockWebDriver webDriver;
    private Browser<DefaultWebElements> browser;

    @Before
    public void setup() {
        webDriver = new MockWebDriver();
        browser = new WebDriverBrowser<>(webDriver, DefaultWebElements.class);
    }

    @Test
    public void testAdd() {
        webDriver.when("$().add(\":text\")").thenReturn(new MockWebElement());

        DefaultWebElements emptyAddSomething = browser.$().add(":text");
        assertThat(emptyAddSomething.size(), Matchers.equalTo(1));
    }
}
