package com.vilt.minium.impl;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.FluentIterable;
import com.vilt.minium.driver.WebElementsDriver;

public abstract class ComposedWebElementsImpl<T extends WebElementsImpl<T>> extends WebElementsImpl<T> {

	private Supplier<List<T>> composingElementsFn;

	public void initComposingWebElements(Supplier<List<T>> composingElementsFn) {
		this.composingElementsFn = composingElementsFn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <R> R invoke(final Method method, final Object... args) {
		WebElementsDriver<T> wd = this.webDriver();
		ComposedWebElementsImpl<T> newComposedElements = (ComposedWebElementsImpl<T>) factory.create(wd, ComposedWebElementsImpl.class);
		
		if (method.getReturnType().isAssignableFrom(newComposedElements.getClass())) {
			newComposedElements.initComposingWebElements(new Supplier<List<T>>() {

				@Override
				public List<T> get() {
					List<T> newComposingElements = FluentIterable.
							from(getComposingElements()).
							transform(new Function<T, T>() {
								@Override
								@Nullable
								public T apply(@Nullable T input) {
									return input.invoke(method, args);
								}
							}).
							toImmutableList();
					return newComposingElements;
				}
			});
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
	
	protected List<T> getComposingElements() {
		return composingElementsFn.get();
	}

}
