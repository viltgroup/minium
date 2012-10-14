package com.vilt.minium.driver;

import org.openqa.selenium.WebDriver;

import com.vilt.minium.WebElements;
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
	@SuppressWarnings("unchecked")
	public DefaultWebElementsDriver(WebDriver wd) {
		super(wd, DefaultWebElements.class, (Class<? extends WebElements<?>>) DebugWebElements.class);
	}

}
