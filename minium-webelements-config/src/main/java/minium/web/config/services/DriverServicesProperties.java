/*
 * Copyright (C) 2015 The Minium Authors
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
package minium.web.config.services;

import java.io.File;
import java.util.List;

import org.openqa.selenium.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.collect.Lists;

import minium.web.config.WebDriverFactory.WebDriverTransformer;

public class DriverServicesProperties implements DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverServicesProperties.class);

    @Value("${app.home:.}")
    private File homedir;
    private ChromeDriverServiceProperties chrome;
    private FirefoxDriverServiceProperties firefox;
    private InternetExplorerDriverServiceProperties internetExplorer;
    private List<Class<WebDriverTransformer>> webDriverTransformerClasses = Lists.newArrayList();

    public ChromeDriverServiceProperties getChrome() {
        if (chrome == null) {
            File chromedriverExe = findExecutable("chromedriver");
            if (chromedriverExe != null) {
                LOGGER.debug("Chrome driver found at {}", chromedriverExe.getAbsolutePath());
                chrome = new ChromeDriverServiceProperties();
                chrome.setDriverExecutable(chromedriverExe);
                chrome.setSilent(true);
            }
        }
        return chrome;
    }

    public void setChrome(ChromeDriverServiceProperties chrome) {
        this.chrome = chrome;
    }

    public FirefoxDriverServiceProperties getFirefox() {
        if (firefox == null) {
            File firefoxDriveExe = findExecutable("geckodriver");
            if (firefoxDriveExe != null) {
                LOGGER.debug("Firefox driver found at {}", firefoxDriveExe.getAbsolutePath());
                firefox = new FirefoxDriverServiceProperties();
                firefox.setDriverExecutable(firefoxDriveExe);
            }
        }
        return firefox;
    }

    public void setFirefox(FirefoxDriverServiceProperties firefox) {
        this.firefox = firefox;
    }

    public InternetExplorerDriverServiceProperties getInternetExplorer() {
        if (internetExplorer == null) {
            File ieDriverExe = findExecutable("IEDriverServer");
            if (ieDriverExe != null) {
                LOGGER.debug("IE driver server found at {}", ieDriverExe.getAbsolutePath());
                internetExplorer = new InternetExplorerDriverServiceProperties();
                internetExplorer.setDriverExecutable(ieDriverExe);
            }
        }
        return internetExplorer;
    }

    public void setInternetExplorer(InternetExplorerDriverServiceProperties internetExplorer) {
        this.internetExplorer = internetExplorer;
    }

    protected File findExecutable(String exeName) {
        File driversDir = getDriversDir();
        if (driversDir == null) return null;

        String osSpecificExeName = Platform.getCurrent().is(Platform.WINDOWS) ? exeName + ".exe" : exeName;
        File exeFile = new File(driversDir, osSpecificExeName);

        return exeFile.exists() && exeFile.isFile() && exeFile.canExecute() ? exeFile : null;
    }

    protected File getDriversDir() {
        File driverDir = homedir == null ? null : new File(homedir, "drivers");
        return driverDir != null && driverDir.exists() && driverDir.isDirectory() ? driverDir : null;
    }

    public List<Class<WebDriverTransformer>> getWebDriverTransformerClasses() {
        return webDriverTransformerClasses;
    }

    public void setWebDriverTransformerClasses(List<Class<WebDriverTransformer>> webDriverTransformerClasses) {
        this.webDriverTransformerClasses = webDriverTransformerClasses;
    }

    public List<WebDriverTransformer> getWebDriverTranformers() {
        List<WebDriverTransformer> transformers = Lists.newArrayList();
        for (Class<WebDriverTransformer> transformerClass : webDriverTransformerClasses) {
            try {
                transformers.add(transformerClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.warn("Could not instantiate " + transformerClass.getCanonicalName(), e);
            }
        }
        return transformers;
    }

    @Override
    public void destroy() throws Exception {
        if (chrome != null) chrome.destroy();
        if (firefox != null) firefox.destroy();
        if (internetExplorer != null) internetExplorer.destroy();
    }
}
