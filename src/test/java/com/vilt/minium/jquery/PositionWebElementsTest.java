package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vilt.minium.MiniumBaseTest;

public class PositionWebElementsTest extends MiniumBaseTest {

	@BeforeTest
	public void openPage() {
		get("minium/tests/position-test.html");
	}
		
	@Test
	public void testRightOf() {
		assertThat($(wd, "td").rightOf($(wd, "#middle-left")), hasSize(2));
		assertThat($(wd, "td").rightOf($(wd, "#middle-center")), hasSize(1));
		assertThat($(wd, "td").rightOf($(wd, "#middle-right")), hasSize(0));
	}
	
	@Test
	public void testLeftOf() {
		assertThat($(wd, "td").leftOf($(wd, "#middle-left")), hasSize(0));
		assertThat($(wd, "td").leftOf($(wd, "#middle-center")), hasSize(1));
		assertThat($(wd, "td").leftOf($(wd, "#middle-right")), hasSize(2));
	}
	
	@Test
	public void testAbove() {
		assertThat($(wd, "td").above($(wd, "#top-center")), hasSize(0));
		assertThat($(wd, "td").above($(wd, "#middle-center")), hasSize(1));
		assertThat($(wd, "td").above($(wd, "#bottom-center")), hasSize(2));
	}
	
	@Test
	public void testBelow() {
		assertThat($(wd, "td").below($(wd, "#top-center")), hasSize(2));
		assertThat($(wd, "td").below($(wd, "#middle-center")), hasSize(1));
		assertThat($(wd, "td").below($(wd, "#bottom-center")), hasSize(0));
	}
}
