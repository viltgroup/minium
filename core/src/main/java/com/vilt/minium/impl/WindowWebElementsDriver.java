package com.vilt.minium.impl;

import static java.lang.String.format;

import org.apache.commons.lang.StringUtils;

import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;

public class WindowWebElementsDriver<T extends WebElements> extends WebElementsDriver<T> {

	public WindowWebElementsDriver(WebElementsDriver<T> wd, WebElementsFactory factory, String handle) {
		super(wd.getWrappedWebDriver(), factory, wd.configuration(), handle);
	}
	
	@Override
	public String toString() {
		if (StringUtils.isEmpty(windowHandle)) {
			return "window";
		}
		else {
			return format("window(handle='%s')", windowHandle);
		}
	}
}
