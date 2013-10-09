package com.vilt.minium.script;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriverService.Builder;
import org.openqa.selenium.chrome.ChromeOptions;
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

public class WebElementsDriverFactory {
	
	private static final String MINIUM_HOME_KEY = "minium.home";

	private static final Logger logger = LoggerFactory.getLogger(WebElementsDriverFactory.class);
	
	private static WebElementsDriverFactory instance;

	private ChromeDriverService service;
	private ChromeDriverService miniumService;

    private Class<? extends WebElement>[] additionalInterfaces;

	@SuppressWarnings("unchecked")
    public static WebElementsDriverFactory instance() {
		if (instance == null) {
			instance = new WebElementsDriverFactory();
		}
		return instance;
	}

	public WebElementsDriverFactory(Class<? extends WebElement> ... additionalInterfaces) {
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
	
	public void maybeInitMiniumDriverService() {
		try {
			if (miniumService == null) {
				miniumService = new Builder()
					.usingAnyFreePort()
					.usingDriverExecutable(new File(miniumBaseDir(), 
							SystemUtils.IS_OS_WINDOWS ? "drivers/chromedriver.exe" : "drivers/chromedriver"))
					.build();
				miniumService.start();
				logger.debug("Minium driver service initialized: {}", miniumService.getUrl());
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
	
	public DefaultWebElementsDriver miniumDriver() {
		maybeInitMiniumDriverService();
		
		ChromeOptions options = new ChromeOptions();
		String chromeExe;
		if (SystemUtils.IS_OS_WINDOWS) {
			chromeExe = "chrome/chrome.exe";
		} else if (SystemUtils.IS_OS_MAC) {
			chromeExe = "chrome/Chromium.app/Contents/MacOS/Chromium";
		} else {
			chromeExe = "chrome/chrome";
		}
		
		options.setBinary(new File(miniumBaseDir(), chromeExe));

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		WebDriver wrappedDriver = new RemoteWebDriver(miniumService.getUrl(), capabilities);
		return createWebElementsDriver(wrappedDriver);
	}
	
	public DefaultWebElementsDriver webDriverFor(DesiredCapabilities capabilities) {
		WebDriver wrappedDriver = null;
		
		if (DesiredCapabilities.chrome().getBrowserName().equals(capabilities.getBrowserName())) {
			maybeInitChromeDriverService();
			wrappedDriver = new RemoteWebDriver(service.getUrl(), capabilities);
			wrappedDriver = new Augmenter().augment(wrappedDriver);
		}
		else if (DesiredCapabilities.firefox().getBrowserName().equals(capabilities.getBrowserName())) {
			wrappedDriver = new FirefoxDriver(capabilities);
		}
		else if (DesiredCapabilities.safari().getBrowserName().equals(capabilities.getBrowserName())) {
			wrappedDriver = new SafariDriver(capabilities);
		}
		else if (DesiredCapabilities.internetExplorer().getBrowserName().equals(capabilities.getBrowserName())) {
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
    
	private static File miniumBaseDir() {
		String path = System.getProperty(MINIUM_HOME_KEY);
		checkNotNull(path);

		File file = new File(path);
		checkState(file.exists() && file.isDirectory());
		
		return file.getAbsoluteFile();
	}
}
