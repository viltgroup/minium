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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.MiniumBaseTest;

public class FiltersWebElementsTest extends MiniumBaseTest {

	@BeforeMethod
	public void openPage() {
		get("minium/tests/filters-test.html");
	}
	
	@Test
	public void testWithLabel() {
		DefaultWebElements nameInputElem = $(wd, "input").withLabel("Name");
		DefaultWebElements passInputElem = $(wd, "input").withLabel("Password");
		
		assertThat(nameInputElem, hasSize(1));
		assertThat(nameInputElem.attr("name"), equalTo("name"));
				
		assertThat(passInputElem, hasSize(1));
		assertThat((String) passInputElem.attr("name"), equalTo("password"));
	}
	
	@Test
	public void testWithText() {
		
	}
	
	@Test
	public void testWithTagNames() {
		
	}
	
	@Test
	public void testContainingText() {
		
	}
	
	@Test
	public void testMatchingText() {
		
	}
}
