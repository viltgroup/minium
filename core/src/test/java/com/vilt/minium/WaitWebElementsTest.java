package com.vilt.minium;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.Minium.untilNotEmpty;

import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;

public class WaitWebElementsTest extends MiniumBaseTest {

	@BeforeTest
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test(expectedExceptions = TimeoutException.class)
	public void testUnexistingElement() {
		$(wd, "#no-element").wait(untilNotEmpty());
	}
	
	@Test
	public void testExistingElement() {
		DefaultWebElements wait = $(wd, "input").wait(untilNotEmpty());
		Assert.assertTrue(Iterables.size(wait) > 0);
	}
}
