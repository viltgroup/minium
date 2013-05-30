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

import static java.lang.String.format;

import org.apache.commons.lang3.StringUtils;

import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;

public class WindowWebElementsDriver<T extends WebElements> extends WebElementsDriver<T> {

	public WindowWebElementsDriver(WebElementsDriver<T> wd, WebElementsFactory factory, String handle) {
		super(wd.getWrappedWebDriver(), factory, wd.configuration(), handle);
	}
	
	@Override
	public String toString() {
		if (StringUtils.isEmpty(windowHandle)) {
			return "window";
		}
		else {
			return format("window(handle='%s')", windowHandle);
		}
	}
}
