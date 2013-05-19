package com.vilt.minium.impl;

import static com.vilt.minium.impl.utils.Casts.cast;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.DefaultWebElementsDriver;
import com.vilt.minium.JQueryWebElements;

public class WebElementsFactoryTest {

	@Test
	public void testInterfaces() {
		WebElementsFactory factory = new WebElementsFactory(DefaultWebElements.class);
		DefaultWebElementsDriver wd = mock(DefaultWebElementsDriver.class);
		DefaultWebElements elem = cast(WebElementsFactoryHelper.createRootWebElements(factory, wd));
		assertTrue(elem instanceof JQueryWebElements<?>);
	}

	@Test
	public void testResources() {
		WebElementsFactory factory = new WebElementsFactory(DefaultWebElements.class);
		JQueryInvoker invoker = factory.getInvoker();
		assertThat(invoker.getJsResources(), contains("minium/js/jquery.min.js", "minium/js/position.js"));
	}
}
