package com.vilt.minium;

import com.vilt.minium.driver.WebElementsDriver;

/**
 * The Interface WebElementsDriverProvider.
 *
 * @param <T> the generic type
 */
public interface WebElementsDriverProvider<T extends WebElements> {

	/**
	 * Web drivers.
	 *
	 * @return the iterable
	 */
	public Iterable<WebElementsDriver<T>> webDrivers();
	
	/**
	 * Web driver.
	 *
	 * @return the web elements driver
	 */
	public WebElementsDriver<T> webDriver();
}
