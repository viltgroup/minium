package com.vilt.minium.impl.elements;

import java.util.Collections;

import org.openqa.selenium.WebElement;

import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;

public abstract class BaseRootWebElementsImpl<T extends WebElements> extends BaseWebElementsImpl<T> {

	@Override
	protected Iterable<WebElement> computeElements(final WebElementsDriver<T> wd) {
		return Collections.emptyList();
	}
	
	@Override
	protected String getExpression() {
		return "$()";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected T relativeRootWebElements() {
		return (T) this;
	}
	
}
