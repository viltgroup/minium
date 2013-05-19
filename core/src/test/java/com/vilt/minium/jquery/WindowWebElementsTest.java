package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.Minium.untilNotEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;
import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.MiniumException;

public class WindowWebElementsTest extends MiniumBaseTest {

	@BeforeTest
	public void openPage() {
		get("minium/tests/window-test.html");
	}
	
	@Test
	public void testWindows() {
		DefaultWebElements elems = $(wd).window().find("input#name").waitOrTimeout(untilNotEmpty());
		assertEquals(1, Iterables.size(elems));
	}
	
	@Test
	public void testWindowsWithFrames() {
		DefaultWebElements elems = $(wd).window().frame().find("input#name");
		assertEquals(2, Iterables.size(elems));
	}
	
	@Test
	public void testWindowsWitnObjectResult() {
		DefaultWebElements input = $(wd).window().find("input#name");
		
		String html = input.html();
		
		assertThat(html, notNullValue());
	}
	
	@Test
	public void testWindowsWithObjectResultFailed() {
		try {
			DefaultWebElements input = $(wd).window().find("h1");
			
			/* String html = */ input.html();
			
			Assert.fail("An exception was expected");
		} catch (MiniumException e) {
			// ok
		}
	}
}
