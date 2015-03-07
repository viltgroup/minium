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
package minium.web.internal.expression;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import minium.web.DocumentWebDriver;
import minium.web.internal.drivers.DocumentWebElement;
import minium.web.internal.drivers.InternalDocumentWebDriver;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

public class ExpressionTest {

    private DocumentWebDriver webDriver = mock(InternalDocumentWebDriver.class);

    @Test
    public void testBasicExpression() {
        // given
        Expression expression = new BasicExpression("1");
        VariableGenerator varGenerator = new VariableGenerator.Impl();

        // when
        String javascript = expression.getJavascript(varGenerator);
        Object[] args = expression.getArgs();

        // then
        assertThat(varGenerator.usedVariables(), equalTo(0));
        assertThat(javascript, equalTo("1"));
        assertThat(args, nullValue());
    }

    @Test
    public void testRootExpression() {
        // given
        Expression expression = new RootExpression();
        VariableGenerator varGenerator = new VariableGenerator.Impl();

        // when
        String javascript = expression.getJavascript(varGenerator);
        Object[] args = expression.getArgs();

        // then
        assertThat(varGenerator.usedVariables(), equalTo(0));
        assertThat(javascript, equalTo("$(\":eq(0)\")"));
        assertThat(args, nullValue());
    }

    @Test
    public void testNativeWebElementsExpression() {
        // given
        List<WebElement> nativeWebElements = Lists.newArrayList(
                createWrappedWebElement(),
                createWebElement(),
                createWrappedWebElement());

        Expression expression = new NativeWebElementsExpression(nativeWebElements);
        VariableGenerator varGenerator = new VariableGenerator.Impl();

        // when
        String javascript = expression.getJavascript(varGenerator);
        Object[] args = expression.getArgs();

        // then
        assertThat(varGenerator.usedVariables(), equalTo(1));
        assertThat(javascript, equalTo("$(args[0])"));
        assertThat(args, arrayWithSize(1));
        assertThat(args[0], instanceOf(List.class));

        @SuppressWarnings("unchecked")
        List<Object> arg = (List<Object>) args[0];

        assertThat(arg, everyItem(instanceOf(WebElement.class)));
        assertThat(arg, everyItem(not(instanceOf(DocumentWebElement.class))));
    }

    @Test
    public void testFunctionInvocationExpressionNativeWithNativeParent() {
        // given
        Expression nativeExpression = new NativeWebElementsExpression(
                createWrappedWebElement(),
                createWebElement(),
                createWrappedWebElement());

        Expression nativeArgExpression = new NativeWebElementsExpression(createWrappedWebElement());
        Expression fn1Expression = new FunctionInvocationExpression(nativeExpression, "parents", new BasicExpression("\"div\""));
        Expression expression = new FunctionInvocationExpression(fn1Expression, "filter", nativeArgExpression);

        VariableGenerator varGenerator = new VariableGenerator.Impl();

        // when
        String javascript = expression.getJavascript(varGenerator);
        Object[] args = expression.getArgs();

        // then
        assertThat(varGenerator.usedVariables(), equalTo(2));
        assertThat(javascript, equalTo("$(args[0]).parents(\"div\").filter($(args[1]))"));
        assertThat(args, arrayWithSize(2));
        assertThat(args[0], instanceOf(List.class));

        assertThat((List<?>) args[0], hasSize(3));
        assertThat((List<?>) args[1], hasSize(1));
    }

    @Test
    public void testFunctionInvocationExpressionWithRootParent() {
        // given
        Expression nativeArgExpression = new NativeWebElementsExpression(createWrappedWebElement());
        Expression fn1Expression = new FunctionInvocationExpression(new RootExpression(), "parents", new BasicExpression("\"div\""));
        Expression expression = new FunctionInvocationExpression(fn1Expression, "filter", nativeArgExpression);

        VariableGenerator varGenerator = new VariableGenerator.Impl();

        // when
        String javascript = expression.getJavascript(varGenerator);
        Object[] args = expression.getArgs();

        // then
        assertThat(varGenerator.usedVariables(), equalTo(1));
        assertThat(javascript, equalTo("$(\":eq(0)\").parents(\"div\").filter($(args[0]))"));
        assertThat(args, arrayWithSize(1));
        assertThat(args[0], instanceOf(List.class));

        assertThat((List<?>) args[0], hasSize(1));
    }

    @Test
    public void testFunctionInvocationExpressionWithFind() {
        // given
        Expression expression = new FunctionInvocationExpression(new RootExpression(), "find", new BasicExpression("\"div\""));
        VariableGenerator varGenerator = new VariableGenerator.Impl();

        // when
        String javascript = expression.getJavascript(varGenerator);
        Object[] args = expression.getArgs();

        // then
        assertThat(varGenerator.usedVariables(), equalTo(0));
        assertThat(javascript, equalTo("$(\"div\")"));
        assertThat(args, emptyArray());
    }

    protected DocumentWebElement createWrappedWebElement() {
        return new DocumentWebElement(createWebElement(), webDriver);
    }

    protected WebElement createWebElement() {
        return mock(WebElement.class);
    }
}
