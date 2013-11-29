package com.vilt.minium.script;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
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

public class WebElementsDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(WebElementsDriverFactory.class);

    private static WebElementsDriverFactory instance;

    private ChromeDriverService service;
    private ChromeDriverService miniumService;

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
        String chromeBin = Configuration.getInstance().get("chrome.bin");
        if (!StringUtils.isEmpty(chromeBin)) {
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
        if (miniumService != null) {
            miniumService.stop();
        }
    }

    protected DefaultWebElementsDriver createWebElementsDriver(WebDriver webDriver) {
        return new DefaultWebElementsDriver(webDriver, additionalInterfaces);
    }
}
