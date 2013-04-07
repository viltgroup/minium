package com.vilt.minium.impl;

import java.util.Collections;

import com.google.common.base.Objects;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.impl.utils.Casts;

public class RootWebElementsImpl<T extends WebElements> extends DocumentRootWebElementsImpl<T> {

	private WebElementsDriver<T> wd;

	public void init(WebElementsFactory factory, WebElementsDriver<?> wd) {
		super.init(factory);
		this.wd = Casts.<WebElementsDriver<T>>cast(wd);
	}
	
	@Override
	protected Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		return Collections.<WebElementsDriver<T>>singletonList(wd);
	}
	
	@Override
	protected WebElementsDriver<T> rootWebDriver() {
		return wd;
	}
	
	@Override
	public T root(T filter, boolean freeze) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof RootWebElementsImpl) {
			RootWebElementsImpl<T> elem = (RootWebElementsImpl<T>) obj;
			return Objects.equal(elem.wd, this.wd);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(wd);
	}
}
