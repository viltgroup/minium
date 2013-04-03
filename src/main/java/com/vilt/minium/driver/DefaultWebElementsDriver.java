package com.vilt.minium.driver;

import org.openqa.selenium.WebDriver;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.tips.TipWebElements;

/**
 * The Class DefaultWebElementsDriver.
 *
 * @author Rui
 */
public class DefaultWebElementsDriver extends WebElementsDriver<DefaultWebElements> {

	/**
	 * Instantiates a new default web elements driver.
	 *
	 * @param wd the wd
	 */
	public DefaultWebElementsDriver(WebDriver wd) {
		this(wd, DefaultWebElements.class, DebugWebElements.class, TipWebElements.class);
	}
	
	/**
	 * Instantiates a new default web elements driver.
	 *
	 * @param wd the wd
	 * @param moreInterfaces the more interfaces
	 */
	public DefaultWebElementsDriver(WebDriver wd, Class<?> ... moreInterfaces) {
		super(wd, DefaultWebElements.class, moreInterfaces);
	}
	
}
