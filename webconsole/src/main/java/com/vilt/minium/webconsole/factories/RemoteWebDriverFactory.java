package com.vilt.minium.webconsole.factories;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.collect.Lists;
import com.vilt.minium.DefaultWebElementsDriver;

public class RemoteWebDriverFactory implements WebDriverFactory {

	private URL seleniumServerUrl;

	private static ChromeDriverService service;
	private List<DefaultWebElementsDriver> webDrivers = Lists.newArrayList();

	public static void maybeInitChromeDriver() throws IOException {
		if (service == null) {
			service = new ChromeDriverService.Builder().usingDriverExecutable(new File("path/to/my/chromedriver")).usingAnyFreePort().build();
			service.start();
		}
	}
	  
	public void setSeleniumServerUrl(URL seleniumServerUrl) {
		this.seleniumServerUrl = seleniumServerUrl;
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
		return new DefaultWebElementsDriver(new PhantomJSDriver(null));
	}
	
	public DefaultWebElementsDriver webDriverFor(DesiredCapabilities capabilities) {
		DefaultWebElementsDriver driver = new DefaultWebElementsDriver(new RemoteWebDriver(seleniumServerUrl, capabilities));
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
		try {
			service.stop();
		} catch (Exception e) {
			// what can we do...
		}
	}
}
