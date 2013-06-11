package com.vilt.minium;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class SuiteTest {

	private static PhantomJSDriverService service;
	private static DesiredCapabilities capabilities;
	private static URL serviceUrl;

	@BeforeSuite
	public static void before() throws IOException {
		capabilities = new DesiredCapabilities();

		String remoteUrl = System.getProperty("minium.remote.url");
		if (StringUtils.isEmpty(remoteUrl)) {
			service = PhantomJSDriverService.createDefaultService();
			service.start();
			serviceUrl = service.getUrl();
		}
		else {
			serviceUrl = new URL(remoteUrl);
		}
	}

	@AfterSuite
	public static void after() {
		if (service != null) {
			service.stop();
		}
	}
	
	public static WebDriver createNativeWebDriver() {
		RemoteWebDriver nativeWd = new RemoteWebDriver(serviceUrl, capabilities);
		return nativeWd;
	}
}
