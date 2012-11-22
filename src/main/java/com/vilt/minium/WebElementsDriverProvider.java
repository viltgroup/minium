package com.vilt.minium;

import com.vilt.minium.driver.WebElementsDriver;

public interface WebElementsDriverProvider<T extends WebElements> {

	public Iterable<WebElementsDriver<T>> webDrivers();
	public WebElementsDriver<T> webDriver();
}
