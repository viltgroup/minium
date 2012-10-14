package com.vilt.minium.impl.elements;

import javax.annotation.Nullable;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.driver.impl.FrameWebElementsDriver;
import com.vilt.minium.impl.DelegateWebElement;
import com.vilt.minium.impl.WebElementsFactory;

public class IFrameWebElementsImpl<T extends WebElements<T>> extends BaseRootWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parent;

	public void init(WebElementsFactory factory, BaseWebElementsImpl<T> parent) {
		this.parent = parent;
		super.init(factory);
	}

	@Override
	protected Iterable<WebElementsDriver<T>> webDrivers() {
		return Iterables.transform(parent, new Function<WebElement, WebElementsDriver<T>>() {

			@Override
			@Nullable
			@SuppressWarnings("unchecked")
			public WebElementsDriver<T> apply(@Nullable WebElement input) {
				return new FrameWebElementsDriver<T>((WebElementsDriver<T>) ((DelegateWebElement) input).getWrappedDriver(), factory, input);
			}
			
		});
	}

	@Override
	protected WebElementsDriver<T> rootWebDriver() {
		return parent.rootWebDriver();
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof IFrameWebElementsImpl) {
			IFrameWebElementsImpl<T> elem = (IFrameWebElementsImpl<T>) obj;
			return Objects.equal(elem.parent, this.parent);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(parent);
	}
	
}
