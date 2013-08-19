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
package com.vilt.minium;

import static com.vilt.minium.Minium.$;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vilt.minium.actions.Interactions;

public class AlertTest extends MiniumBaseTest {
	
	@BeforeClass
	public void before() throws IOException {
		wd = new DefaultWebElementsDriver(SuiteTest.createNativeWebDriver());
	}

	@AfterClass
	public void after() {
		wd.quit();
	}
	
	@BeforeMethod
	public void openPage() {
		get("minium/tests/alert-test.html");
	}

	
	@Test(enabled = false)
	public void testAlert() {
		$(wd).alert().accept();
	}
	
	@Test(enabled = false)
	public void testAlertTimeout() {
		wd.configuration().defaultTimeout(3, TimeUnit.SECONDS);
		try {
			$(wd).alert().accept();
		} catch (TimeoutException e) {
			assertTrue(e.getCause() instanceof NoAlertPresentException);
		}
	}

	@Test(enabled = false)
	public void testAlertCatch() {
		wd.configuration().defaultTimeout(3, TimeUnit.SECONDS);
		wd.configuration().registerExceptionHandler(ExceptionHandlers.alwaysAcceptUnhandledAlerts());
		
		Interactions.waitTime(3, SECONDS);		
		DefaultWebElements elem = $(wd, "h2");
		
		assertEquals(elem.text(), "Alert Tests");
	}
}
