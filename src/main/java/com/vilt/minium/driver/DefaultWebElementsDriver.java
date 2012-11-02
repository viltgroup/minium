package com.vilt.minium.driver;

import org.openqa.selenium.WebDriver;

import com.vilt.minium.jquery.DefaultWebElements;
import com.vilt.minium.jquery.debug.DebugWebElements;

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
	public DefaultWebElementsDriver(WebDriver wd) {
		super(wd, DefaultWebElements.class, DebugWebElements.class/*, NewWindowWebElements.class */);
	}

}
