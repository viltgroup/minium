package com.vilt.minium.impl;

import javax.annotation.Nullable;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.driver.FrameWebElementsDriver;
import com.vilt.minium.impl.utils.Casts;

public class FrameWebElementsImpl<T extends WebElements> extends BaseRootWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parent;

	public void init(WebElementsFactory factory, WebElements parent) {
		this.parent = Casts.<BaseWebElementsImpl<T>>cast(parent);
		super.init(factory);
	}

	@Override
	public Iterable<WebElementsDriver<T>> candidateWebDrivers() {
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
	public boolean equals(Object obj) {
		if (obj instanceof FrameWebElementsImpl) {
			FrameWebElementsImpl<T> elem = Casts.<FrameWebElementsImpl<T>>cast(obj);
			return Objects.equal(elem.parent, this.parent);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(parent);
	}
	
}
