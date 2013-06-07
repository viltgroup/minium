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
package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.Minium.whileEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;
import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.MiniumException;

public class WindowWebElementsTest extends MiniumBaseTest {

	@BeforeMethod
	public void openPage() {
		get("minium/tests/window-test.html");
	}
	
	@AfterMethod
	public void closeWindows() {
		$(wd).window().close();
	}
	
	@Test
	public void testWindows() {
		DefaultWebElements elems = $(wd).window().find("input#name").waitOrTimeout(whileEmpty());
		assertEquals(1, Iterables.size(elems));
	}
	
	@Test
	public void testWindowsWithFrames() {
		DefaultWebElements elems = $(wd).window().frame().find("input#name");
		assertEquals(2, Iterables.size(elems));
	}
	
	@Test
	public void testWindowsWitnObjectResult() {
		DefaultWebElements input = $(wd).window().find("input#name");
		
		String html = input.html();
		
		assertThat(html, notNullValue());
	}
	
	@Test
	public void testWindowsWithObjectResultFailed() {
		try {
			DefaultWebElements input = $(wd).window().find("h1");
			
			/* String html = */ input.html();
			
			Assert.fail("An exception was expected");
		} catch (MiniumException e) {
			// ok
		}
	}
}
