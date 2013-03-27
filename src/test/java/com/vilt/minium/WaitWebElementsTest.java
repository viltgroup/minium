package com.vilt.minium;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.Minium.untilNotEmpty;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;

import com.google.common.collect.Iterables;

public class WaitWebElementsTest extends MiniumBaseTest {

	@Before
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test(expected = TimeoutException.class)
	public void testUnexistingElement() {
		$(wd, "#no-element").wait(untilNotEmpty());
	}
	
	@Test
	public void testExistingElement() {
		DefaultWebElements wait = $(wd, "input").wait(untilNotEmpty());
		Assert.assertTrue(Iterables.size(wait) > 0);
	}
}
