package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.vilt.minium.MiniumBaseTest;

public class JQueryWebElementsTest extends MiniumBaseTest {

	@Before
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test
	public void testTextInputs() {
		assertThat(Iterables.size($(wd, "input:text")), equalTo(1));
		assertThat(Iterables.size($(wd, "input:password")), equalTo(1));
	}
	
}
