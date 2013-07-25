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

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;

public class WebElementsFactoryHelper {

	@SuppressWarnings("unchecked")
	public static <T extends CoreWebElements<T>> T createExpressionWebElements(WebElementsFactory factory, T parent, String fnName, Object ... args) {
		ExpressionWebElementsImpl<T> webElements = factory.create(ExpressionWebElementsImpl.class);
		webElements.init(factory, parent, fnName, args);
		return (T) webElements;
	}
	
	public static <T extends CoreWebElements<T>> T createWindowWebElements(WebElementsFactory factory, T parent) {
		return WebElementsFactoryHelper.<T>createWindowWebElements(factory, parent, (T) null, false);
	}
	
	public static <T extends CoreWebElements<T>> T createWindowWebElements(WebElementsFactory factory, T parent, String expr, boolean freeze) {
		return WebElementsFactoryHelper.<T>createWindowWebElements(factory, parent, expr == null ? null : parent.window().find(expr), freeze);
	}

	@SuppressWarnings("unchecked")
	public static <T extends CoreWebElements<T>> T createWindowWebElements(WebElementsFactory factory, T parent, T filter, boolean freeze) {
		if (freeze) {
			FrozenWindowWebElementsImpl<T> webElements = factory.create(FrozenWindowWebElementsImpl.class);
			webElements.init(factory, parent, filter);
			return (T) webElements;
		}
		else {
			WindowWebElementsImpl<T> webElements = factory.create(WindowWebElementsImpl.class);
			webElements.init(factory, parent, filter);
			return (T) webElements;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends CoreWebElements<T>> T createFrameWebElements(WebElementsFactory factory, T parent, T filter, boolean freeze) {
		if (freeze) {
			FrozenFrameWebElementsImpl<T> webElements = factory.create(FrozenFrameWebElementsImpl.class);
			webElements.init(factory, parent, filter);
			return (T) webElements;
		}
		else {
			FrameWebElementsImpl<T> webElements = factory.create(FrameWebElementsImpl.class);
			webElements.init(factory, parent, filter);
			return (T) webElements;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends CoreWebElements<T>> T createRootWebElements(WebElementsFactory factory, WebElementsDriver<?> driver) {
		RootWebElementsImpl<?> webElements = factory.create(RootWebElementsImpl.class);
		webElements.init(factory, driver);
		return (T) webElements;
	}
}
