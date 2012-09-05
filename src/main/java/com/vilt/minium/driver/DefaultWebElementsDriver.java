package com.vilt.minium.driver;

import org.openqa.selenium.WebDriver;

import com.vilt.minium.jquery.DefaultWebElements;

/**
 * 
 * @author Rui
 *
 */
public class DefaultWebElementsDriver extends WebElementsDriver<DefaultWebElements> {

	/**
	 * 
	 * @param wd
	 */
	@SuppressWarnings("unchecked")
	public DefaultWebElementsDriver(WebDriver wd) {
		super(wd, DefaultWebElements.class);
	}

}
