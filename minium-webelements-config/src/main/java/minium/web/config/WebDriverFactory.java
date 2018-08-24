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
package minium.web.config;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import minium.web.StatefulWebDriver;
import minium.web.config.WebDriverProperties.ChromeOptionsProperties;
import minium.web.config.WebDriverProperties.DimensionProperties;
import minium.web.config.WebDriverProperties.FirefoxProfileProperties;
import minium.web.config.WebDriverProperties.PointProperties;
import minium.web.config.WebDriverProperties.PreferenceProperties;
import minium.web.config.WebDriverProperties.WindowProperties;
import minium.web.config.services.ChromeDriverServiceProperties;
import minium.web.config.services.DriverServicesProperties;
import minium.web.config.services.FirefoxDriverServiceProperties;
import minium.web.config.services.InternetExplorerDriverServiceProperties;

public class WebDriverFactory {

    public interface WebDriverTransformer {
        public WebDriver transform(WebDriver wd);
    }

    private final DriverServicesProperties driverServices;

    enum WebDriverType {
        CHROME(BrowserType.CHROME, BrowserType.GOOGLECHROME) {
            @Override
            public WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities) {
                ChromeDriverServiceProperties serviceProperties = webDriverFactory.driverServices == null ? null : webDriverFactory.driverServices.getChrome();
                DriverService driverService = serviceProperties == null ? null : serviceProperties.getDriverService();
                return driverService == null ?
                        new ChromeDriver(new ChromeOptions().merge(desiredCapabilities))
                        : new RemoteWebDriver(driverService.getUrl(), desiredCapabilities);
            }
        },
        FIREFOX(BrowserType.FIREFOX) {
            @Override
            public WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities) {
                FirefoxDriverServiceProperties serviceProperties = webDriverFactory.driverServices == null ? null : webDriverFactory.driverServices.getFirefox();
                DriverService driverService = serviceProperties == null ? null : serviceProperties.getDriverService();
                FirefoxOptions firefoxOptions = new FirefoxOptions().merge(desiredCapabilities);
                return driverService == null ? new FirefoxDriver(firefoxOptions) : new RemoteWebDriver(driverService.getUrl(), firefoxOptions);
            }
        },
        IE(BrowserType.IE, BrowserType.IEXPLORE) {
            @Override
            public WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities) {
                InternetExplorerDriverServiceProperties serviceProperties = webDriverFactory.driverServices == null ? null : webDriverFactory.driverServices.getInternetExplorer();
                DriverService driverService = serviceProperties == null ? null : serviceProperties.getDriverService();
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions().merge(desiredCapabilities);
                return driverService == null ? new InternetExplorerDriver(internetExplorerOptions) : new RemoteWebDriver(driverService.getUrl(), internetExplorerOptions);
            }
        },
        SAFARI(BrowserType.SAFARI) {
            @Override
            public WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities) {
                return new SafariDriver(new SafariOptions().merge(desiredCapabilities));
            }
        };

        private Set<String> types;

        private WebDriverType(String ... types) {
            this.types = Sets.newHashSet();
            for (String type : types) {
                this.types.add(type.toLowerCase());
            }
        }

        public abstract WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities);

        public static WebDriverType typeFor(String value) {
            value = value.toLowerCase();
            for (WebDriverType type : WebDriverType.values()) {
                if (type.types.contains(value)) return type;
            }
            throw new IllegalArgumentException(format("Type %s is not valid", value));
        }
    }

    public WebDriverFactory(DriverServicesProperties driverServices) {
        this.driverServices = driverServices;
    }

    public WebDriver create(WebDriverProperties webDriverProperties) throws IOException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities(webDriverProperties.getDesiredCapabilities());
        desiredCapabilities.merge(browserSpecificCapabilities(webDriverProperties));

        WebDriver webDriver = null;
        if (webDriverProperties.getUrl() != null) {
            RemoteWebDriver remoteDriver = new RemoteWebDriver(webDriverProperties.getUrl(), desiredCapabilities);
            remoteDriver.setFileDetector(new LocalFileDetector());
            webDriver = remoteDriver;
        } else {
            String browserName = desiredCapabilities == null ? null : desiredCapabilities.getBrowserName();
            if (Strings.isNullOrEmpty(browserName)) browserName = BrowserType.CHROME;
            webDriver = WebDriverType.typeFor(browserName).create(this, desiredCapabilities);
        }

        WindowProperties window = webDriverProperties.getWindow();
        if (window != null) {
            DimensionProperties size = window.getSize();
            PointProperties position = window.getPosition();
            if (size != null) webDriver.manage().window().setSize(new Dimension(size.getWidth(), size.getHeight()));
            if (position != null) webDriver.manage().window().setPosition(new Point(position.getX(), position.getY()));
            if (window.isMaximized()) {
                webDriver.manage().window().maximize();
            }
        }

        webDriver = webDriver instanceof TakesScreenshot ? webDriver : new Augmenter().augment(webDriver);
        for (WebDriverTransformer transformer : driverServices.getWebDriverTranformers()) webDriver = transformer.transform(webDriver);
        return webDriverProperties.isStateful() ? new StatefulWebDriver(webDriver) : webDriver;
    }

    private Capabilities browserSpecificCapabilities(WebDriverProperties webDriverProperties) throws IOException {
        ChromeOptionsProperties chromeProperties = webDriverProperties.getChromeOptions();
        if (chromeProperties != null) return chromeCapabilities(chromeProperties);

        FirefoxProfileProperties firefoxProperties = webDriverProperties.getFirefoxProfile();
        if (firefoxProperties != null) return firefoxCapabilities(firefoxProperties);

        return new MutableCapabilities();
    }

    private Capabilities chromeCapabilities(ChromeOptionsProperties chromeProperties) {
        ChromeOptions options = new ChromeOptions();
        if (chromeProperties.getArgs() != null) options.addArguments(chromeProperties.getArgs());
        if (chromeProperties.getBinary() != null) options.setBinary(chromeProperties.getBinary());
        if (chromeProperties.getExtensions() != null) options.addExtensions(chromeProperties.getExtensions());
        if (chromeProperties.getPreferences() != null) options.setExperimentalOption("prefs", chromeProperties.getPreferences());
        return options;
    }

    private Capabilities firefoxCapabilities(FirefoxProfileProperties firefoxProperties) throws IOException {
        File profileDirectory = null;
        String profileDir = firefoxProperties.getDir();
        if (profileDir != null) {
            profileDirectory = new File(profileDir);
        }
        FirefoxProfile profile = new FirefoxProfile(profileDirectory);

        List<PreferenceProperties> preferences = firefoxProperties.getPreferences();
        if (preferences != null) {
            for (PreferenceProperties preference : preferences) {
                switch (preference.getType()) {
                case BOOLEAN:
                    profile.setPreference(preference.getName(), (Boolean) preference.getValue());
                    break;
                case INTEGER:
                    profile.setPreference(preference.getName(), (Integer) preference.getValue());
                    break;
                case STRING:
                    profile.setPreference(preference.getName(), (String) preference.getValue());
                }
            }
        }

        List<File> extensions = firefoxProperties.getExtensions();
        if (extensions != null) extensions.stream().forEach(profile::addExtension);

        profile.setAlwaysLoadNoFocusLib(firefoxProperties.shouldLoadNoFocusLib());
        profile.setAcceptUntrustedCertificates(firefoxProperties.shouldAcceptUntrustedCerts());
        profile.setAssumeUntrustedCertificateIssuer(firefoxProperties.shouldUntrustedCertIssuer());

        return new FirefoxOptions().setProfile(profile);
    }
}
