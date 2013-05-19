package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;
import com.vilt.minium.MiniumBaseTest;

public class JQueryWebElementsTest extends MiniumBaseTest {

	@BeforeTest
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test
	public void testTextInputs() {
		assertThat(Iterables.size($(wd, "input:text")), equalTo(1));
		assertThat(Iterables.size($(wd, "input:password")), equalTo(1));
	}
	
}
