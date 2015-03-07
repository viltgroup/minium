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

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;
import minium.web.internal.WebElementFunctions;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

public class NativeWebElementsExpression extends BaseExpression {

    private final List<? extends WebElement> nativeWebElements;

    public NativeWebElementsExpression(WebElement ... nativeWebElements) {
        this(Lists.newArrayList(nativeWebElements));
    }

    public NativeWebElementsExpression(List<? extends WebElement> nativeWebElements) {
        this.nativeWebElements = nativeWebElements;
    }

    @Override
    public String getJavascript(VariableGenerator varGenerator) {
        return format("$(%s)", varGenerator.generate());
    }

    @Override
    public Object[] getArgs() {
        List<WebElement> unwrappedNativedWebElements = from(nativeWebElements).transform(WebElementFunctions.unwrap()).toList();
        return new Object[] { unwrappedNativedWebElements };
    }

}
