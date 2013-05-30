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
package com.vilt.minium;

import org.openqa.selenium.WebDriver;

import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.tips.TipWebElements;

/**
 * The Class DefaultWebElementsDriver.
 *
 * @author Rui
 */
public class DefaultWebElementsDriver extends WebElementsDriver<DefaultWebElements> {

	/**
	 * Instantiates a new default web elements driver.
	 *
	 * @param wd the wd
	 */
	public DefaultWebElementsDriver(WebDriver wd) {
		this(wd, DefaultWebElements.class, DebugWebElements.class, TipWebElements.class);
	}
	
	/**
	 * Instantiates a new default web elements driver.
	 *
	 * @param wd the wd
	 * @param moreInterfaces the more interfaces
	 */
	public DefaultWebElementsDriver(WebDriver wd, Class<?> ... moreInterfaces) {
		super(wd, DefaultWebElements.class, moreInterfaces);
	}
	
}
