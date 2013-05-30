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
package com.vilt.minium.webconsole.factories;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.collect.Lists;
import com.vilt.minium.DefaultWebElementsDriver;

public class RemoteWebDriverFactory implements WebDriverFactory {

	private URL seleniumServerUrl;

	private static ChromeDriverService service;
	private List<DefaultWebElementsDriver> webDrivers = Lists.newArrayList();

	public static void maybeInitChromeDriver() throws IOException {
		if (service == null) {
			service = new ChromeDriverService.Builder().usingDriverExecutable(new File("path/to/my/chromedriver")).usingAnyFreePort().build();
			service.start();
		}
	}
	  
	public void setSeleniumServerUrl(URL seleniumServerUrl) {
		this.seleniumServerUrl = seleniumServerUrl;
	}
	
	public DefaultWebElementsDriver chromeDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		return webDriverFor(capabilities);
	}
	
	public DefaultWebElementsDriver firefoxDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		return webDriverFor(capabilities);
	}
	
	public DefaultWebElementsDriver internetExplorerDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		return webDriverFor(capabilities);
	}
	
	public DefaultWebElementsDriver ghostDriver() {
		return new DefaultWebElementsDriver(new PhantomJSDriver(null));
	}
	
	public DefaultWebElementsDriver webDriverFor(DesiredCapabilities capabilities) {
		DefaultWebElementsDriver driver = new DefaultWebElementsDriver(new RemoteWebDriver(seleniumServerUrl, capabilities));
		webDrivers.add(driver);
		return driver;
	}

	@Override
	public void destroy() {
		for (DefaultWebElementsDriver driver : webDrivers) {
			try {
				driver.quit();
			} catch (Exception e) {
				// no big deal
			}
		}
		try {
			service.stop();
		} catch (Exception e) {
			// what can we do...
		}
	}
}
