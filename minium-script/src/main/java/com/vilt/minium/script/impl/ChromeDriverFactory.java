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
package com.vilt.minium.script.impl;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.vilt.minium.prefs.AppPreferences;
import com.vilt.minium.prefs.WebConsolePreferences;

public class ChromeDriverFactory implements WebDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(ChromeDriverFactory.class);

    private ChromeDriverService service;
    private AppPreferences preferences;

    public ChromeDriverFactory(AppPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean supports(DesiredCapabilities capabilities) {
        return Objects.equal(BrowserType.CHROME, capabilities.getBrowserName());
    }

    @Override
    public WebDriver create(DesiredCapabilities capabilities) {
        Preconditions.checkArgument(Objects.equal(BrowserType.CHROME, capabilities.getBrowserName()));
        maybeInitChromeDriverService();
        File chromeBin = WebConsolePreferences.from(preferences).getChromeBin();
        if (chromeBin.exists()) {
            // clone capabilities
            capabilities = new DesiredCapabilities(capabilities);
            capabilities.setCapability("chrome.binary", chromeBin);
        }
        WebDriver driver = new RemoteWebDriver(service.getUrl(), capabilities);
        return new Augmenter().augment(driver);
    }

    @Override
    public void destroy() {
        if (service != null) {
            logger.debug("Stopping Chrome driver service: {}", service.getUrl());

            service.stop();
        }
    }

    protected void maybeInitChromeDriverService() {
        try {
            if (service == null) {
                String exe = Platform.getCurrent().is(Platform.WINDOWS) ? "chromedriver.exe" : "chromedriver";
                File  baseWebDriver = new File(new File(preferences.getBaseDir(), "drivers"), exe);

                if (baseWebDriver.isFile() && baseWebDriver.canExecute()) {
                    service = new ChromeDriverService.Builder().usingDriverExecutable(baseWebDriver).usingAnyFreePort().build();
                } else {
                    service = ChromeDriverService.createDefaultService();
                }
                service.start();
                logger.debug("Chrome driver service initialized: {}", service.getUrl());
            }
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
