package com.vilt.minium.impl.elements;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.openqa.selenium.WebElement;

import com.google.common.collect.Iterators;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.jquery.JQueryWebElements;

public abstract class RootWebElementsImpl<T extends JQueryWebElements<T>> extends BaseWebElementsImpl<T> {
	
	private WebElementsDriver<T> wd;

	protected void init(WebElementsDriver<T> wd) {
		this.wd = wd;
	}
	
	@Override
	public WebElementsDriver<T> webDriver() {
		return wd;
	}
	
	@Override
	public Iterator<WebElement> iterator() {
		return Iterators.emptyIterator();
	}
	
	@Override
	protected <R> R invoke(Method method, Object... args) {

		SimpleWebElementsImpl<T> childWebElements = createChildWebElements();
		childWebElements.init(this, method.getName(), args);
		
		if (method.getReturnType().isAssignableFrom(childWebElements.getClass())) {
			return (R) childWebElements;
		}
		
	}
	
	protected SimpleWebElementsImpl<T> createChildWebElements() {
		SimpleWebElementsImpl<T> webElements = (SimpleWebElementsImpl<T>) getFactory().create(wd, SimpleWebElementsImpl.class);
	}
}
