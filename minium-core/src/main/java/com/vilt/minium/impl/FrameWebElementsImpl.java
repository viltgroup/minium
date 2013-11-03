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

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.FluentIterable.from;

import javax.annotation.Nullable;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;

public class FrameWebElementsImpl<T extends CoreWebElements<T>> extends DocumentRootWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parentImpl;
	private T filter;

	@SuppressWarnings("unchecked")
	public void init(WebElementsFactory factory, T parent, T filter) {
		super.init(factory);
		this.parentImpl = (BaseWebElementsImpl<T>) parent;
		this.filter = (T) filter;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		 return from(parentImpl).transform(new Function<WebElement, WebElementsDriver<T>>() {
			@Override
			@Nullable
			public WebElementsDriver<T> apply(@Nullable WebElement input) {
				FrameWebElementsDriver<T> webElementsDriver = new FrameWebElementsDriver<T>((WebElementsDriver<T>) ((DelegateWebElement) input).getWrappedDriver(), factory, input);
				if (filter != null && webElementsDriver.find(filter).size() == 0) {
					return null;
				}
				return webElementsDriver;
			}
		}).filter(notNull());
	}
	
	@Override
	protected T root(T filter, boolean freeze) {
		return parentImpl.frames(filter, freeze);
	}

	@Override
	public WebElementsDriver<T> rootWebDriver() {
		return parentImpl.rootWebDriver();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof FrameWebElementsImpl) {
			FrameWebElementsImpl<T> elem = (FrameWebElementsImpl<T>) obj;
			return 
				Objects.equal(elem.parentImpl, this.parentImpl) && 
				Objects.equal(elem.filter, this.filter);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(parentImpl);
	}
	
}
