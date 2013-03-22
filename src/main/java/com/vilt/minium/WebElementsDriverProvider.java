package com.vilt.minium;

import org.openqa.selenium.WebDriver;

import com.vilt.minium.driver.Configuration;
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

	public Configuration configuration();
	
	public WebDriver nativeWebDriver();
}
