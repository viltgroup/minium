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

import org.openqa.selenium.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;

public class DriverServicesProperties implements DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverServicesProperties.class);

    @Value("${app.home:.}")
    private File homedir;
    private ChromeDriverServiceProperties chrome;
    private InternetExplorerDriverServiceProperties internetExplorer;
    private PhantomJsDriverServiceProperties phantomJs;

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

    public PhantomJsDriverServiceProperties getPhantomJs() {
        if (phantomJs == null) {
            File phantomjsExe = findExecutable("phantomjs");
            if (phantomjsExe != null) {
                LOGGER.debug("PhantomJS found at {}", phantomjsExe.getAbsolutePath());
                phantomJs = new PhantomJsDriverServiceProperties();
                phantomJs.setDriverExecutable(phantomjsExe);
            }
        }
        return phantomJs;
    }

    public void setPhantomJs(PhantomJsDriverServiceProperties phamtomJs) {
        this.phantomJs = phamtomJs;
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

    @Override
    public void destroy() throws Exception {
        if (chrome != null) chrome.destroy();
        if (internetExplorer != null) internetExplorer.destroy();
        if (phantomJs != null) phantomJs.destroy();
    }
}
