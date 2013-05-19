package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.MiniumBaseTest;

public class FiltersWebElementsTest extends MiniumBaseTest {

	@BeforeTest
	public void openPage() {
		get("minium/tests/filters-test.html");
	}
	
	@Test
	public void testWithLabel() {
		DefaultWebElements nameInputElem = $(wd, "input").withLabel("Name");
		DefaultWebElements passInputElem = $(wd, "input").withLabel("Password");
		
		assertThat(nameInputElem, hasSize(1));
		assertThat(nameInputElem.attr("name"), equalTo("name"));
				
		assertThat(passInputElem, hasSize(1));
		assertThat((String) passInputElem.attr("name"), equalTo("password"));
	}
	
	@Test
	public void testWithText() {
		
	}
	
	@Test
	public void testWithTagNames() {
		
	}
	
	@Test
	public void testContainingText() {
		
	}
	
	@Test
	public void testMatchingText() {
		
	}
}
