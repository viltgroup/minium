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
package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vilt.minium.MiniumBaseTest;

public class PositionWebElementsTest extends MiniumBaseTest {

	@BeforeTest
	public void openPage() {
		get("minium/tests/position-test.html");
	}
		
	@Test
	public void testRightOf() {
		assertThat($(wd, "td").rightOf($(wd, "#middle-left")), hasSize(2));
		assertThat($(wd, "td").rightOf($(wd, "#middle-center")), hasSize(1));
		assertThat($(wd, "td").rightOf($(wd, "#middle-right")), hasSize(0));
	}
	
	@Test
	public void testLeftOf() {
		assertThat($(wd, "td").leftOf($(wd, "#middle-left")), hasSize(0));
		assertThat($(wd, "td").leftOf($(wd, "#middle-center")), hasSize(1));
		assertThat($(wd, "td").leftOf($(wd, "#middle-right")), hasSize(2));
	}
	
	@Test
	public void testAbove() {
		assertThat($(wd, "td").above($(wd, "#top-center")), hasSize(0));
		assertThat($(wd, "td").above($(wd, "#middle-center")), hasSize(1));
		assertThat($(wd, "td").above($(wd, "#bottom-center")), hasSize(2));
	}
	
	@Test
	public void testBelow() {
		assertThat($(wd, "td").below($(wd, "#top-center")), hasSize(2));
		assertThat($(wd, "td").below($(wd, "#middle-center")), hasSize(1));
		assertThat($(wd, "td").below($(wd, "#bottom-center")), hasSize(0));
	}
}
