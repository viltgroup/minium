package com.vilt.minium.impl;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.jquery.DefaultWebElements;

public class WebElementsFactoryTest {

	@Test
	@SuppressWarnings("unchecked")
	public void testInterfaces() {
		WebElementsFactory<DefaultWebElements> factory = new WebElementsFactory<DefaultWebElements>(DefaultWebElements.class);
		DefaultWebElements elem = factory.create(mock(WebElementsDriver.class));
		assertThat((JQueryWebElementsImpl<?>) elem, isA(JQueryWebElementsImpl.class));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testResources() {
		WebElementsFactory<DefaultWebElements> factory = new WebElementsFactory<DefaultWebElements>(DefaultWebElements.class);
		JQueryInvoker invoker = factory.getInvoker();
		assertThat(invoker.getJsResources(), contains("motion/js/jquery.js", "motion/js/position.js"));
	}
}
