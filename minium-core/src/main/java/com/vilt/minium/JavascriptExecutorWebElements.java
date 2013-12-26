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
package com.vilt.minium;

@JQueryResources("minium/internal/js/jquery.jsexecutor.js")
public interface JavascriptExecutorWebElements<T extends CoreWebElements<T>> extends WebElements {

    public <R> R call(JavascriptFunction fn, Object... args);

    public T callWebElements(JavascriptFunction fn, Object... args);

    @Async
    public <R> R callAsync(JavascriptFunction fn, Object... args);

    public <R> R eval(String script, Object... args);

    public T evalWebElements(String script, Object... args);

    @Async
    public <R> R evalAsync(String script, Object... args);
}
