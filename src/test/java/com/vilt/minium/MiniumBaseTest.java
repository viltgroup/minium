package com.vilt.minium;

import java.net.URL;

import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.vilt.minium.driver.DefaultWebElementsDriver;

public class MiniumBaseTest {
	
	protected DefaultWebElementsDriver wd;
	
	@Before
	public void before() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		wd = new DefaultWebElementsDriver(new ChromeDriver(capabilities));
	}
	
	@After
	public void after() {
		wd.quit();
	}
	
	protected void get(String resourcePath) {
		URL resource = MiniumBaseTest.class.getClassLoader().getResource(resourcePath);
		wd.get(resource.toExternalForm());
	}
	
	public Matcher<Iterable<WebElement>> hasSize(int size) {
		return IsIterableWithSize.<WebElement>iterableWithSize(size);		
	}
}