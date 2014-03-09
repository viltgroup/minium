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
import org.openqa.selenium.ie.InternetExplorerDriverService;
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

public class InternetExplorerDriverFactory implements WebDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(InternetExplorerDriverFactory.class);

    private InternetExplorerDriverService service;
    private AppPreferences preferences;

    public InternetExplorerDriverFactory(AppPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean supports(DesiredCapabilities capabilities) {
        return Objects.equal(BrowserType.IE, capabilities.getBrowserName());
    }

    @Override
    public WebDriver create(DesiredCapabilities capabilities) {
        Preconditions.checkArgument(Objects.equal(BrowserType.IE, capabilities.getBrowserName()));
        Preconditions.checkState(Platform.getCurrent().is(Platform.WINDOWS), "IE driver only supported in Windows");
        maybeInitInternetExplorerDriverService();
        WebDriver driver = new RemoteWebDriver(service.getUrl(), capabilities);
        return new Augmenter().augment(driver);
    }

    @Override
    public void destroy() {
        if (service != null) {
            logger.debug("Stopping IE driver service: {}", service.getUrl());

            service.stop();
        }
    }

    protected void maybeInitInternetExplorerDriverService() {
        try {
            if (service == null) {
                File  baseWebDriver = new File(new File(preferences.getBaseDir(), "drivers"), "IEDriverServer.exe");

                if (baseWebDriver.isFile() && baseWebDriver.canExecute()) {
                    service = new InternetExplorerDriverService.Builder().usingDriverExecutable(baseWebDriver).usingAnyFreePort().build();
                } else {
                    service = InternetExplorerDriverService.createDefaultService();
                }
                service.start();
                logger.debug("IE driver service initialized: {}", service.getUrl());
            }
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
