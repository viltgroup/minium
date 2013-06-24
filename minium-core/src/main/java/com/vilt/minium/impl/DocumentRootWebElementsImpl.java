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

import org.openqa.selenium.WebElement;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;

public abstract class DocumentRootWebElementsImpl<T extends CoreWebElements<T>> extends BaseWebElementsImpl<T> {

	/**
	 * we return the root element from that page
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<WebElement> computeElements(final WebElementsDriver<T> wd) {
		BaseWebElementsImpl<T> impl = (BaseWebElementsImpl<T>) myself.find(":eq(0)");
		return impl.computeElements(wd);
	}

	@Override
	protected String getExpression() {
		return "$(':eq(0)')";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected T documentRootWebElements() {
		return (T) this;
	}
	
}
