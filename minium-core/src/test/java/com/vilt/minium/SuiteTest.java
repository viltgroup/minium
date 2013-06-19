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

import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class SuiteTest {

	private static PhantomJSDriverService service;
	private static DesiredCapabilities capabilities;
	private static URL serviceUrl;

	@BeforeSuite
	public static void before() throws IOException {
		capabilities = new DesiredCapabilities();

		String remoteUrl = System.getProperty("minium.remote.url");
		if (StringUtils.isEmpty(remoteUrl)) {
			service = PhantomJSDriverService.createDefaultService();
			service.start();
			serviceUrl = service.getUrl();
		}
		else {
			serviceUrl = new URL(remoteUrl);
		}
	}

	@AfterSuite
	public static void after() {
		if (service != null) {
			service.stop();
		}
	}
	
	public static WebDriver createNativeWebDriver() {
		RemoteWebDriver nativeWd = new RemoteWebDriver(serviceUrl, capabilities);
		return nativeWd;
	}
}
