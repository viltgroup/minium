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

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.WebElementsDriverProvider;

public class WindowWebElementsImpl<T extends CoreWebElements<T>> extends DocumentRootWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parentImpl;
	private String handle;
	private T filter;
	private boolean freeze;

	public void init(WebElementsFactory factory, T parent, String expr, boolean freeze) {
		init(factory, parent, parent.window().find(expr), freeze);
	}

	@SuppressWarnings("unchecked")
	public void init(WebElementsFactory factory, T parent, T filter, boolean freeze) {
		super.init(factory);
		this.parentImpl = (BaseWebElementsImpl<T>) parent;
		this.filter = filter;
		this.freeze = freeze;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		final WebElementsDriver<T> wd = rootWebDriver();
		final String currentHandle = wd.getWindowHandle();
		
		if (filter != null) {
			Iterable<WebElementsDriver<T>> webDrivers = ((WebElementsDriverProvider<T>) filter).webDrivers();
			if (Iterables.size(webDrivers) == 1) {
				handle = Iterables.get(webDrivers, 0).getWindowHandle();
				if (freeze) {
					// this way we won't evaluate filter ever again, so we will keep using the
					// same window!
					filter = null;
				}
			}
		}
		else if (freeze && handle == null) {
			//we are going to try to capture a new window
			throw new UnsupportedOperationException("To be implemented...");
		}
		
		Set<String> windowHandles;
		if (StringUtils.isNotEmpty(handle)) {
			windowHandles = Sets.newHashSet(Collections.singleton(handle));
		}
		else {
			windowHandles = Sets.newHashSet(wd.getWindowHandles());
		}

		windowHandles.remove(currentHandle);
		
		if (windowHandles.isEmpty()) {
			return Collections.emptyList();
		}
		else {
			return FluentIterable
					.from(windowHandles)
					.transform(new Function<String, WebElementsDriver<T>>() {
						@Override
						@Nullable
						public WebElementsDriver<T> apply(@Nullable String input) {
							return new WindowWebElementsDriver<T>(wd, factory, input);
						}
					}).toList();
		}
	}

	@Override
	protected WebElementsDriver<T> rootWebDriver() {
		return parentImpl.rootWebDriver();
	}
	
	@Override
	protected T root(T filter, boolean freeze) {
		return parentImpl.window(filter, freeze);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof WindowWebElementsImpl) {
			WindowWebElementsImpl<T> elem = (WindowWebElementsImpl<T>) obj;
			return 
					Objects.equal(elem.parentImpl, this.parentImpl) && 
					Objects.equal(elem.handle, this.handle);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(parentImpl, handle);
	}


}
