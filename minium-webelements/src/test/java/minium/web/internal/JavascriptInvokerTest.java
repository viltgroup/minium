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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import minium.web.internal.drivers.DefaultJavascriptInvoker;
import minium.web.internal.drivers.JavascriptInvoker;

import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.google.common.collect.Lists;

public class JavascriptInvokerTest {

    interface TestWebDriver extends WebDriver, JavascriptExecutor { }

    @SuppressWarnings("unchecked")
    @Test
    public void testLightInvokerScript() {
        // given
        List<String> jsResources = Collections.emptyList();
        List<String> cssResources = Collections.emptyList();
        JavascriptInvoker invoker = new DefaultJavascriptInvoker(JavascriptInvokerTest.class.getClassLoader(), jsResources, cssResources);

        List<Integer> expectedResult = Lists.newArrayList(1, 2, 3);
        TestWebDriver wd = mock(TestWebDriver.class);
        when(wd.executeScript(anyString(), anyVararg()))
            // first time, we return the result with true value and the actual response
            .thenReturn(Lists.newArrayList("array", 1, 2, 3));

        // when
        Object result = invoker.invoke(wd, "return [1, 2, 3];");

        verify(wd, times(1)).executeScript(anyString(), anyVararg());
        assertThat(result, instanceOf(List.class));
        assertThat((List<Integer>) result, equalTo(expectedResult));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testLightInvokerScriptCouldNotCompute() {
        // given
        List<String> jsResources = Collections.emptyList();
        List<String> cssResources = Collections.emptyList();
        JavascriptInvoker invoker = new DefaultJavascriptInvoker(JavascriptInvokerTest.class.getClassLoader(), jsResources, cssResources);

        List<Integer> expectedResult = Lists.newArrayList(1, 2, 3);
        TestWebDriver wd = mock(TestWebDriver.class);
        when(wd.executeScript(anyString(), anyVararg()))
            // first time, we return an list with false value to tell invoker it could not compute the result with the light script
            .thenReturn(Lists.newArrayList("minium-undefined"))
            // second time, we return the result with true value and the actual response
            .thenReturn(Lists.newArrayList("array", 1, 2, 3));

        // when
        Object result = invoker.invoke(wd, "return [1, 2, 3];");

        verify(wd, times(2)).executeScript(anyString(), anyVararg());
        assertThat(result, instanceOf(List.class));
        assertThat((List<Integer>) result, equalTo(expectedResult));
    }
}
