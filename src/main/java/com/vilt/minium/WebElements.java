package com.vilt.minium;

import org.openqa.selenium.WebElement;

/**
 * 
 * @author Rui
 *
 * @param <T>
 */
public interface WebElements extends Iterable<WebElement> {

	public WebElement get(int index);

}
