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

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.openqa.selenium.JavascriptExecutor;

/**
 * <p>Minium methods that perform some kind of client asynchronous operation must have
 * this annotation. Right now, only methods that <strong>do not return</strong> 
 * {@link WebElements} are allowed to be marked as asynchronous.</p>
 * <p>In pratical terms, instead of calling the method 
 * {@link JavascriptExecutor#executeScript(String, Object...)} to evaluate a
 * Minium expression, the engine will call 
 * {@link JavascriptExecutor#executeAsyncScript(String, Object...)}.</p>
 * <p>Because of that, the javascript function corresponding to the declared method must receive an
 * extra argument that will correspond to the <code>callback</code> function, which must 
 * be used to return this function asynchronously.</p>
 * 
 * @see JavascriptExecutor#executeAsyncScript(String, Object...)
 * 
 * @author rui.figueira
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface Async { }
