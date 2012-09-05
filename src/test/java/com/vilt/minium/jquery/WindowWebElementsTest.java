package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.vilt.minium.MiniumBaseTest;

public class WindowWebElementsTest extends MiniumBaseTest {

	@Before
	public void openPage() {
		get("minium/tests/window-test.html");
	}
	
	@Test
	public void testWindows() {
		DefaultWebElements elems = $(wd).window().find("input#name");
		assertThat(elems, hasSize(2));
	}
}
