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

import javax.annotation.Nullable;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.impl.utils.Casts;

public class FrameWebElementsImpl<T extends WebElements> extends DocumentRootWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parent;

	public void init(WebElementsFactory factory, WebElements parent) {
		this.parent = Casts.<BaseWebElementsImpl<T>>cast(parent);
		super.init(factory);
	}

	@Override
	public Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		return Iterables.transform(parent, new Function<WebElement, WebElementsDriver<T>>() {

			@Override
			@Nullable
			@SuppressWarnings("unchecked")
			public WebElementsDriver<T> apply(@Nullable WebElement input) {
				return new FrameWebElementsDriver<T>((WebElementsDriver<T>) ((DelegateWebElement) input).getWrappedDriver(), factory, input);
			}
			
		});
	}

	@Override
	protected WebElementsDriver<T> rootWebDriver() {
		return parent.rootWebDriver();
	}
	
	@Override
	public T root(T filter, boolean freeze) {
		return parent.frame(filter, freeze);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FrameWebElementsImpl) {
			FrameWebElementsImpl<T> elem = Casts.<FrameWebElementsImpl<T>>cast(obj);
			return Objects.equal(elem.parent, this.parent);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(parent);
	}
	
}
