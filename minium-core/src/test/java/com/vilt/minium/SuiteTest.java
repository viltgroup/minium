package com.vilt.minium;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class SuiteTest {

	private static PhantomJSDriverService service;
	private static DesiredCapabilities capabilities;
//	private static ChromeDriverService service;

	@BeforeSuite
	public static void before() throws IOException {
		capabilities = new DesiredCapabilities();
		service = PhantomJSDriverService.createDefaultService();
		service.start();
	}

	@AfterSuite
	public static void after() {
		service.stop();
	}
	
	public static WebDriver createNativeWebDriver() {
		RemoteWebDriver nativeWd = new RemoteWebDriver(service.getUrl(), capabilities);
		return nativeWd;
	}
}
