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

import static java.lang.String.format;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.vilt.minium.DefaultWebElementsDriver;
import com.vilt.minium.WebElements;
import com.vilt.minium.prefs.AppPreferences;
import com.vilt.minium.script.impl.ChromeDriverFactory;
import com.vilt.minium.script.impl.FirefoxDriverFactory;
import com.vilt.minium.script.impl.InternetExplorerDriverFactory;
import com.vilt.minium.script.impl.PhantomJsDriverFactory;
import com.vilt.minium.script.impl.SafariDriverFactory;
import com.vilt.minium.script.impl.WebDriverFactory;

public class WebElementsDriverFactory {

    private static WebElementsDriverFactory instance;

    private Class<? extends WebElements>[] additionalInterfaces;
    private Map<String, WebDriverFactory> driverFactories = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    public static WebElementsDriverFactory instance() throws IOException {
        if (instance == null) {
            instance = new WebElementsDriverFactory(new AppPreferences());
        }
        return instance;
    }

    public WebElementsDriverFactory(AppPreferences preferences, Class<? extends WebElements>... additionalInterfaces) {
        this.additionalInterfaces = additionalInterfaces;

        driverFactories.put(BrowserType.CHROME, new ChromeDriverFactory(preferences));
        driverFactories.put(BrowserType.FIREFOX, new FirefoxDriverFactory());
        driverFactories.put(BrowserType.IE, new InternetExplorerDriverFactory(preferences));
        driverFactories.put(BrowserType.SAFARI, new SafariDriverFactory());
        driverFactories.put(BrowserType.PHANTOMJS, new PhantomJsDriverFactory(preferences));
    }

    public void setPreferences(AppPreferences preferences) {
    }

    public DefaultWebElementsDriver chromeDriver() {
        return webDriverFor(DesiredCapabilities.chrome());
    }

    public DefaultWebElementsDriver firefoxDriver() {
        return webDriverFor(DesiredCapabilities.firefox());
    }

    public DefaultWebElementsDriver internetExplorerDriver() {
        return webDriverFor(DesiredCapabilities.internetExplorer());
    }

    public DefaultWebElementsDriver safariDriver() {
        return webDriverFor(DesiredCapabilities.safari());
    }

    public DefaultWebElementsDriver phantomjsDriver() {
        return webDriverFor(DesiredCapabilities.phantomjs());
    }

    public DefaultWebElementsDriver remoteDriver(String url, Capabilities capabilities) {
        try {
            WebDriver wrappedDriver = new RemoteWebDriver(new URL(url), capabilities);
            return new DefaultWebElementsDriver(wrappedDriver);
        } catch (MalformedURLException e) {
            throw Throwables.propagate(e);
        }
    }

    public DefaultWebElementsDriver webDriverFor(DesiredCapabilities capabilities) {
        String browserName = capabilities.getBrowserName();
        WebDriverFactory factory = driverFactories.get(browserName);

        if (factory == null) {
            for (WebDriverFactory candidate : driverFactories.values()) {
                if (candidate.supports(capabilities)) {
                    factory = candidate;
                    break;
                }
            }
        }

        if (factory == null) throw new IllegalArgumentException(format("No suitable driver for capabilities %s", capabilities));

        return createWebElementsDriver(factory.create(capabilities));
    }

    public void destroy() {
        for (WebDriverFactory factory : driverFactories.values()) {
            factory.destroy();
        }
    }

    protected DefaultWebElementsDriver createWebElementsDriver(WebDriver webDriver) {
        return new DefaultWebElementsDriver(webDriver, additionalInterfaces);
    }
}
