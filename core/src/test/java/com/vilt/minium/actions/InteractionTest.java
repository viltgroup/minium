package com.vilt.minium.actions;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.actions.Interactions.click;
import static com.vilt.minium.tips.TipInteractions.withTip;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.MiniumBaseTest;

public class InteractionTest extends MiniumBaseTest {
	
	@BeforeTest
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}

	@Test
	public void testClick() {
		click($(wd).find(":submit"));
	}
	
	@Test
	public void testPerform() {
		DefaultWebElements submitBtn = $(wd, ":submit");
		
	}
	
	@Test
	public void testPerformWithTips() {
		DefaultWebElements submitBtn = $(wd, ":submit");
		withTip("Let's click this button!").perform(null);
	}
}
