package com.vilt.minium.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.collect.Lists;

public class JQueryInvokerWebDriverTest {

	private JQueryInvoker positionInvoker;
	private ChromeDriver wd;

	@Before
	public void before() {
		positionInvoker = new JQueryInvoker(Lists.newArrayList("minium/js/jquery.js", "minium/js/position.js"), null);
		wd = new ChromeDriver();
	}
	
	@After
	public void after() {
		wd.quit();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testGoogleHelloWorld() {

		wd.get("http://www.google.com");
		Object result = positionInvoker.invoke(wd, "return $('input')");
		
		List<WebElement> webElements = (List<WebElement>) result;
		assertThat(webElements, everyItem(isA(WebElement.class)));		
	}
	
	@Test
	public void testGoogleHelloWorldWithArg() {

		wd.get("http://www.google.com");
		Object result = positionInvoker.invoke(wd, "return args[0];", "Hello World");
		
		assertThat((String) result, equalTo("Hello World"));		
	}
	
	@Test
	public void testGoogleHelloWorldWithArgFullAndLight() {

		wd.get("http://www.google.com");
		Object result = positionInvoker.invoke(wd, "return args[0];", "Hello World");
		
		assertThat((String) result, equalTo("Hello World"));
		
		result = positionInvoker.invoke(wd, "return args[0];", "Hello World (now light)");
		assertThat((String) result, equalTo("Hello World (now light)"));
	}
	
}
