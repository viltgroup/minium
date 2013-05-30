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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class JQueryInvokerTest {

	private JQueryInvoker positionInvoker;

	@BeforeTest
	public void before() {
		positionInvoker = new JQueryInvoker(Lists.newArrayList("minium/js/jquery.min.js", "minium/js/position.js"), null);
	}
	
	@Test
	public void testLoadJsResourcesFull() {
		String script = positionInvoker.fullInvokerScript("return 'Hello world!';");
		
		assertThat(script, containsString("$.fn.below"));
	}
	
	@Test
	public void testLoadJsResourcesLight() {
		String script = positionInvoker.lightInvokerScript("return 'Hello world!';");
		assertThat(script, containsString("Hello world"));
		assertThat(script, not(containsString("$.fn.below")));
	}
	
}
