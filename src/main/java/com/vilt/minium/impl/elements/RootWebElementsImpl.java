package com.vilt.minium.impl.elements;

import java.util.Collections;

import com.google.common.base.Objects;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.WebElementsFactory;

public class RootWebElementsImpl<T extends WebElements> extends BaseRootWebElementsImpl<T> {

	private WebElementsDriver<T> wd;

	public void init(WebElementsFactory factory, WebElementsDriver<T> wd) {
		super.init(factory);
		this.wd = wd;
	}
	
	@Override
	protected Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		return Collections.singletonList(wd);
	}
	
	@Override
	protected WebElementsDriver<T> rootWebDriver() {
		return wd;
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
