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
package minium.web.internal.drivers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.google.common.collect.Lists;

public class JavascriptInvokerTest {

    // adapted from
    // https://piotrga.wordpress.com/2009/03/27/hamcrest-regex-matcher/#comment-638
    public static class RegularExpressionFindMatcher extends TypeSafeMatcher<String> {

        private final Pattern pattern;

        public RegularExpressionFindMatcher(String pattern) {
            this(Pattern.compile(pattern));
        }
        public RegularExpressionFindMatcher(Pattern pattern) {
            this.pattern = pattern;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("matches regular expression ").appendValue(pattern);
        }

        @Override
        public boolean matchesSafely(String item) {
            return pattern.matcher(item).find();
        }

        public static Matcher<String> hasPattern(Pattern pattern) {
            return new RegularExpressionFindMatcher(pattern);
        }

        public static Matcher<String> hasPattern(String pattern) {
            return new RegularExpressionFindMatcher(pattern);
        }
    }

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
        Object result = invoker.invoke(wd, "[1, 2, 3]");

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
        Object result = invoker.invoke(wd, "[1, 2, 3]");

        verify(wd, times(2)).executeScript(anyString(), anyVararg());
        assertThat(result, instanceOf(List.class));
        assertThat((List<Integer>) result, equalTo(expectedResult));
    }

    @Test
    public void testNoOneLineCommentsAndNewlines() {
        // given
        List<String> jsResources = Collections.emptyList();
        List<String> cssResources = Collections.emptyList();
        DefaultJavascriptInvoker invoker = new DefaultJavascriptInvoker(JavascriptInvokerTest.class.getClassLoader(), jsResources, cssResources);

        // ensure it has no one-line comments
        assertThat(invoker.lightInvokerScript(), not(RegularExpressionFindMatcher.hasPattern("^\\s*\\/\\/")));
        assertThat(invoker.fullInvokerScript(), not(RegularExpressionFindMatcher.hasPattern("^\\s*\\/\\/")));

        // ensure it has no newlines
        assertThat(invoker.lightInvokerScript(), not(RegularExpressionFindMatcher.hasPattern("\\r?\\n")));
        assertThat(invoker.fullInvokerScript(), not(RegularExpressionFindMatcher.hasPattern("\\r?\\n")));
    }

}
