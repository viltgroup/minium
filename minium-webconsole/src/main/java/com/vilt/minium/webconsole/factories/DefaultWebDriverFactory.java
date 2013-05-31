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
package com.vilt.minium.webconsole.factories;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.vilt.minium.DefaultWebElementsDriver;

public class DefaultWebDriverFactory implements WebDriverFactory {

	private static final Logger logger = LoggerFactory.getLogger(DefaultWebDriverFactory.class);
	
	private static ChromeDriverService service;

	private List<DefaultWebElementsDriver> webDrivers = Lists.newArrayList();

	public static void maybeInitChromeDriver() {
		try {
			if (service == null) {
				service = ChromeDriverService.createDefaultService();
				service.start();
				logger.debug("Chrome driver service initialized: {}", service.getUrl());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
		return new DefaultWebElementsDriver(new PhantomJSDriver(new DesiredCapabilities()));
	}
	
	public DefaultWebElementsDriver webDriverFor(DesiredCapabilities capabilities) {
		WebDriver wrappedDriver = null;
		
		if (DesiredCapabilities.chrome().getBrowserName().equals(capabilities.getBrowserName())) {
			maybeInitChromeDriver();
			wrappedDriver = new RemoteWebDriver(service.getUrl(), capabilities);
		}
		else if (DesiredCapabilities.firefox().getBrowserName().equals(capabilities.getBrowserName())) {
			wrappedDriver = new FirefoxDriver(capabilities);
		}
		else if (DesiredCapabilities.firefox().getBrowserName().equals(capabilities.getBrowserName())) {
			wrappedDriver = new FirefoxDriver(capabilities);
		}
		else if (DesiredCapabilities.internetExplorer().getBrowserName().equals(capabilities.getBrowserName())) {
			wrappedDriver = new InternetExplorerDriver(capabilities);
		}
		
		if (wrappedDriver == null) {
			throw new IllegalArgumentException();
		}

		DefaultWebElementsDriver driver = new DefaultWebElementsDriver(wrappedDriver);
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
	}
}
