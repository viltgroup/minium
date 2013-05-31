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
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AlertTest extends MiniumBaseTest {

	@BeforeTest
	public void openPage() {
		get("minium/tests/alert-test.html");
	}

	
	@Test
	public void testAlert() {
		$(wd).alert().accept();
	}
	
	@Test
	public void testAlertTimeout() {
		wd.configuration().defaultTimeout(3, TimeUnit.SECONDS);
		try {
			$(wd).alert().accept();
		} catch (TimeoutException e) {
			assertTrue(e.getCause() instanceof NoAlertPresentException);
		}
	}
	
	@Test
	public void testJQueryWhileAlert() throws InterruptedException {
		Sleeper.SYSTEM_SLEEPER.sleep(new Duration(3, SECONDS));
		try {
			$(wd, "h2").val();
		} catch (MiniumException e) {
			assertTrue(e.getCause() instanceof UnhandledAlertException);
		}
	}
	
	@Test
	public void testJQueryWhileAlerta() throws InterruptedException {
		Sleeper.SYSTEM_SLEEPER.sleep(new Duration(3, SECONDS));
		try {
			$(wd, "h2").val();
		} catch (MiniumException e) {
			wd.switchTo().alert().accept();
		}
	}
	
}
