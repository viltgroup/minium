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
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.debug.DebugWebElements;

public class DebugWebElementsTest extends MiniumBaseTest {

	@BeforeTest
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test
	public void testDebugInterface() {
		assertTrue($(wd, "input:text") instanceof DebugWebElements);
	}
	
}
