package com.vilt.minium;

import static com.vilt.minium.Minium.$;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.collect.Iterables;
import com.vilt.minium.driver.DefaultWebElementsDriver;
import com.vilt.minium.jquery.DefaultWebElements;

public class MiniumTest extends MiniumBaseTest {
	
	private DefaultWebElementsDriver wd;
	
	@Before
	public void before() {
		wd = new DefaultWebElementsDriver(new ChromeDriver());
	}
	
	@After
	public void after() {
		wd.quit();
	}
	
	@Test
	public void testGoogle() {
		wd.get("http://www.google.com");
		DefaultWebElements elems = $(wd, "button").withText("Google Search");
		assertThat(Iterables.size(elems), equalTo(1));
	}
}
