package com.vilt.minium.driver.impl;

import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.WebElementsFactory;

public class WindowWebElementsDriver<T extends WebElements> extends WebElementsDriver<T> {

	public WindowWebElementsDriver(WebElementsDriver<T> wd, WebElementsFactory factory, String handle) {
		super(wd.getWrappedWebDriver(), factory, handle);
	}
}
