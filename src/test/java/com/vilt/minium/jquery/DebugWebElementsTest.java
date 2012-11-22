package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.jquery.debug.DebugWebElements;

public class DebugWebElementsTest extends MiniumBaseTest {

	@Before
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test
	public void testDebugInterface() {
		assertTrue($(wd, "input:text") instanceof DebugWebElements);
	}
	
}
