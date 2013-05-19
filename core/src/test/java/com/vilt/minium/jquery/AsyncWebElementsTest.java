package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vilt.minium.Async;
import com.vilt.minium.DefaultWebElementsDriver;
import com.vilt.minium.JQueryResources;
import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.WebElements;

public class AsyncWebElementsTest extends MiniumBaseTest {

	@JQueryResources("js/async-test.js")
	public interface AsyncWebElements extends WebElements {
		
		@Async
		public String asyncHelloWorld(String name);
	}
	
	@Override
	public void before() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		wd = new DefaultWebElementsDriver(new ChromeDriver(capabilities), AsyncWebElements.class);
		wd.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
	}

	@BeforeTest
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test
	public void testAsyncHelloWorld() {
		assertEquals("Hello, World!", ((AsyncWebElements) $(wd)).asyncHelloWorld("World"));
	}
}
