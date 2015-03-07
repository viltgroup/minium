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

import org.openqa.selenium.JavascriptExecutor;

public interface JavascriptInvoker {

    public abstract <T> T invoke(JavascriptExecutor wd, String expression, Object ... args);

    public abstract <T> T invokeAsync(JavascriptExecutor wd, String expression, Object ... args);

    public abstract <T> T invokeExpression(JavascriptExecutor executor, String expression, Object ... args);

    public abstract <T> T invokeExpressionAsync(JavascriptExecutor executor, String expression, Object ... args);

}