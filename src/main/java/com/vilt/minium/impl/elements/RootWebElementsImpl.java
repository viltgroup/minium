package com.vilt.minium.impl.elements;

import java.util.Collections;

import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.WebElementsFactory;

public class RootWebElementsImpl<T extends WebElements<T>> extends BaseRootWebElementsImpl<T> {

	private WebElementsDriver<T> wd;

	public void init(WebElementsFactory factory, WebElementsDriver<T> wd) {
		super.init(factory);
		this.wd = wd;
	}
	
	@Override
	protected Iterable<WebElementsDriver<T>> webDrivers() {
		return Collections.singletonList(wd);
	}
	
	@Override
	protected WebElementsDriver<T> rootWebDriver() {
		return wd;
	}
}
