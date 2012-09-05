package com.vilt.minium.impl;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterators;
import com.vilt.minium.driver.WebElementsDriver;

public abstract class BaseComposedWebElementsImpl<T extends JQueryWebElementsImpl<T>> extends JQueryWebElementsImpl<T> {
	
	private WebElementsImpl<?> rootElements;

	public void initParentRootWebElements(WebElementsImpl<?> rootElements) {
		this.rootElements = rootElements;
	}

	protected WebElementsImpl<?> getRootWebElements() {
		return rootElements;
	}
	
	public Iterator<WebElement> iterator() {
		return Iterators.concat(FluentIterable.
				from(getComposingElements()).
				transform(new Function<WebElementsImpl<T>, Iterator<WebElement>>() {
					public Iterator<WebElement> apply(WebElementsImpl<T> input) {
						return input.iterator();
					}
				}).
				toImmutableList().
				iterator());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <R> R invoke(final Method method, final Object... args) {
		WebElementsDriver<T> wd = this.webDriver();
		ComposedJQueryWebElementsImpl<T> newComposedElements = (ComposedJQueryWebElementsImpl<T>) factory.create(wd, ComposedJQueryWebElementsImpl.class);
		newComposedElements.initParentRootWebElements(getRootWebElements());
		
		if (method.getReturnType().isAssignableFrom(newComposedElements.getClass())) {
			newComposedElements.initExpression(this, method.getName(), args);
			return (R) newComposedElements;
		}
		else if (method.getReturnType() == Void.TYPE) {
			for (WebElementsImpl<T> webElements : getComposingElements()) {
				webElements.invoke(method, args);
			}
			return null;
		} else {
			throw new UnsupportedOperationException("Not implemented yet...");
		}	
	}
	
	protected abstract List<WebElementsImpl<T>> getComposingElements();
	
}