package com.vilt.minium.impl;

import org.openqa.selenium.WebElement;

import com.vilt.minium.JQueryWebElements;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.utils.Casts;

public abstract class BaseRootWebElementsImpl<T extends WebElements> extends BaseWebElementsImpl<T> {

	/**
	 * we return the root element from that page
	 */
	@Override
	protected Iterable<WebElement> computeElements(final WebElementsDriver<T> wd) {
		return Casts.<JQueryWebElements<BaseWebElementsImpl<T>>>cast(this).find(":eq(0)").computeElements(wd);
	}

	@Override
	protected String getExpression() {
		return "$(':eq(0)')";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected T relativeRootWebElements() {
		return (T) this;
	}
	
}
