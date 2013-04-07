package com.vilt.minium;

import java.net.URL;

import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableWithSize;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class MiniumBaseTest {
	
	protected DefaultWebElementsDriver wd;
	
	@BeforeSuite
	public void before() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		wd = new DefaultWebElementsDriver(new PhantomJSDriver(capabilities));
	}
	
	@AfterSuite
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