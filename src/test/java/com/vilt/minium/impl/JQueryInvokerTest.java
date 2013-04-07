package com.vilt.minium.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class JQueryInvokerTest {

	private JQueryInvoker positionInvoker;

	@BeforeTest
	public void before() {
		positionInvoker = new JQueryInvoker(Lists.newArrayList("minium/js/jquery.min.js", "minium/js/position.js"), null);
	}
	
	@Test
	public void testLoadJsResourcesFull() {
		String script = positionInvoker.fullInvokerScript("return 'Hello world!';");
		
		assertThat(script, containsString("$.fn.below"));
	}
	
	@Test
	public void testLoadJsResourcesLight() {
		String script = positionInvoker.lightInvokerScript("return 'Hello world!';");
		assertThat(script, containsString("Hello world"));
		assertThat(script, not(containsString("$.fn.below")));
	}
	
}
