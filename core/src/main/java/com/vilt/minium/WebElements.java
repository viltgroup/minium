package com.vilt.minium;

import org.openqa.selenium.WebElement;

/**
 * The Interface WebElements.
 *
 * @author Rui
 */
public interface WebElements extends Iterable<WebElement> {

	/**
	 * Gets the {@link WebElement} at a given position.
	 *
	 * @param index the index
	 * @return the web element
	 */
	public WebElement get(int index);

}
