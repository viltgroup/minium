/*
 * Copyright (C) 2013 VILT Group
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

import java.util.Collections;

import com.google.common.base.Objects;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.impl.utils.Casts;

public class RootWebElementsImpl<T extends WebElements> extends DocumentRootWebElementsImpl<T> {

	private WebElementsDriver<T> wd;

	public void init(WebElementsFactory factory, WebElementsDriver<?> wd) {
		super.init(factory);
		this.wd = Casts.<WebElementsDriver<T>>cast(wd);
	}
	
	@Override
	protected Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		return Collections.<WebElementsDriver<T>>singletonList(wd);
	}
	
	@Override
	protected WebElementsDriver<T> rootWebDriver() {
		return wd;
	}
	
	@Override
	public T root(T filter, boolean freeze) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof RootWebElementsImpl) {
			RootWebElementsImpl<T> elem = (RootWebElementsImpl<T>) obj;
			return Objects.equal(elem.wd, this.wd);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(wd);
	}
}
