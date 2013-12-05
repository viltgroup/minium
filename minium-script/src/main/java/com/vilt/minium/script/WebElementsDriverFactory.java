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
package com.vilt.minium.script;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vilt.minium.DefaultWebElementsDriver;
import com.vilt.minium.WebElements;
import com.vilt.minium.prefs.AppPreferences;
import com.vilt.minium.prefs.WebConsolePreferences;

public class WebElementsDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(WebElementsDriverFactory.class);

    private static WebElementsDriverFactory instance;

    private AppPreferences preferences;

    private ChromeDriverService service;

    private Class<? extends WebElements>[] additionalInterfaces;

    @SuppressWarnings("unchecked")
    public static WebElementsDriverFactory instance() {
        if (instance == null) {
            instance = new WebElementsDriverFactory();
        }
        return instance;
    }

    public WebElementsDriverFactory(Class<? extends WebElements>... additionalInterfaces) {
        this.additionalInterfaces = additionalInterfaces;
    }

    public void setPreferences(AppPreferences preferences) {
        this.preferences = preferences;
    }

    public void maybeInitChromeDriverService() {
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
        File chromeBin = WebConsolePreferences.from(preferences).getChromeBin();
        if (chromeBin.exists()) {
            capabilities.setCapability("chrome.binary", chromeBin);
        }
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

    public DefaultWebElementsDriver safariDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.safari();
        return webDriverFor(capabilities);
    }

    public DefaultWebElementsDriver ghostDriver() {
        PhantomJSDriver webDriver = new PhantomJSDriver(new DesiredCapabilities());
        return createWebElementsDriver(webDriver);
    }

    public DefaultWebElementsDriver remoteDriver(String url, Capabilities capabilities) {
        try {
            WebDriver wrappedDriver = new RemoteWebDriver(new URL(url), capabilities);
            return new DefaultWebElementsDriver(wrappedDriver);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public DefaultWebElementsDriver webDriverFor(DesiredCapabilities capabilities) {
        WebDriver wrappedDriver = null;

        if (DesiredCapabilities.chrome().getBrowserName().equals(capabilities.getBrowserName())) {
            maybeInitChromeDriverService();
            wrappedDriver = new RemoteWebDriver(service.getUrl(), capabilities);
            wrappedDriver = new Augmenter().augment(wrappedDriver);
        } else if (DesiredCapabilities.firefox().getBrowserName().equals(capabilities.getBrowserName())) {
            wrappedDriver = new FirefoxDriver(capabilities);
        } else if (DesiredCapabilities.safari().getBrowserName().equals(capabilities.getBrowserName())) {
            wrappedDriver = new SafariDriver(capabilities);
        } else if (DesiredCapabilities.internetExplorer().getBrowserName().equals(capabilities.getBrowserName())) {
            wrappedDriver = new InternetExplorerDriver(capabilities);
        }

        if (wrappedDriver == null) {
            throw new IllegalArgumentException();
        }

        return createWebElementsDriver(wrappedDriver);
    }

    public void destroy() {
        if (service != null) {
            service.stop();
        }
    }

    protected DefaultWebElementsDriver createWebElementsDriver(WebDriver webDriver) {
        return new DefaultWebElementsDriver(webDriver, additionalInterfaces);
    }
}
