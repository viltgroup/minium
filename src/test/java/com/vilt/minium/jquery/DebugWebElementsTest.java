package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.debug.DebugWebElements;

public class DebugWebElementsTest extends MiniumBaseTest {

	@BeforeTest
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test
	public void testDebugInterface() {
		assertTrue($(wd, "input:text") instanceof DebugWebElements);
	}
	
}
