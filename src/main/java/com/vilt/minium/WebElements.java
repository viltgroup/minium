package com.vilt.minium;

import org.openqa.selenium.WebElement;

import com.vilt.minium.driver.WebElementsDriver;

/**
 * 
 * @author Rui
 *
 * @param <T>
 */
public interface WebElements<T extends WebElements<T>> extends Iterable<WebElement> {
	
	/**
	 * 
	 * @return
	 */
	public WebElementsDriver<T> webDriver();

	public WebElement get(int index);
	
}
