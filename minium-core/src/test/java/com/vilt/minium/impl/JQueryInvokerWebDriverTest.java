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
package com.vilt.minium.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.isA;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.vilt.minium.MiniumBaseTest;

public class JQueryInvokerWebDriverTest extends MiniumBaseTest {

    private JQueryInvoker positionInvoker;

    @BeforeMethod
    public void openPage() {
        get("minium/tests/jquery-test.html");
        positionInvoker = new JQueryInvoker(Lists.newArrayList("minium/jquery/jquery.min.js", "minium/internal/js/jquery.position.js"), null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testInvoke() {
        Object result = positionInvoker.invoke(wd, false, "return $('input')");

        List<WebElement> webElements = (List<WebElement>) result;
        assertThat(webElements, everyItem(isA(WebElement.class)));
    }

    @Test
    public void testInvokeWithArg() {
        Object result = positionInvoker.invoke(wd, "return args[0];", "Hello World");

        assertThat((String) result, equalTo("Hello World"));
    }

    @Test
    public void testInvokeWithArgFullAndLight() {
        Object result = positionInvoker.invoke(wd, "return args[0];", "Hello World");

        assertThat((String) result, equalTo("Hello World"));

        result = positionInvoker.invoke(wd, "return args[0];", "Hello World (now light)");
        assertThat((String) result, equalTo("Hello World (now light)"));
    }

}
