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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.DefaultWebElementsDriver;
import com.vilt.minium.JQueryWebElements;

public class WebElementsFactoryTest {

	@Test
	public void testInterfaces() {
		WebElementsFactory factory = new WebElementsFactory(DefaultWebElements.class);
		DefaultWebElementsDriver wd = mock(DefaultWebElementsDriver.class);
		DefaultWebElements elem = WebElementsFactoryHelper.createRootWebElements(factory, wd);
		assertTrue(elem instanceof JQueryWebElements<?>);
	}

	@Test
	public void testResources() {
		WebElementsFactory factory = new WebElementsFactory(DefaultWebElements.class);
		JQueryInvoker invoker = factory.getInvoker();
		assertThat(invoker.getJsResources(), hasItems("minium/js/jquery.min.js", "minium/js/position.js"));
	}
}
