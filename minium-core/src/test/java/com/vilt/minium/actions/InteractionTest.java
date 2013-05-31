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
package com.vilt.minium.actions;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.actions.Interactions.click;
import static com.vilt.minium.tips.TipInteractions.withTip;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.MiniumBaseTest;

public class InteractionTest extends MiniumBaseTest {
	
	@BeforeTest
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}

	@Test
	public void testClick() {
		click($(wd).find(":submit"));
	}
	
	@Test
	public void testPerform() {
		DefaultWebElements submitBtn = $(wd, ":submit");
		
	}
	
	@Test
	public void testPerformWithTips() {
		DefaultWebElements submitBtn = $(wd, ":submit");
		withTip("Let's click this button!").perform(null);
	}
}
