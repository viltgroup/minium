package com.vilt.minium.impl.elements;

import static java.lang.String.format;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.driver.impl.WindowWebElementsDriver;
import com.vilt.minium.impl.WebElementsFactory;

public class WindowWebElementsImpl<T extends WebElements<T>> extends BaseRootWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parent;
	private String nameOrHandle;

	public void init(WebElementsFactory factory, BaseWebElementsImpl<T> parent, String nameOrHandle) {
		super.init(factory);
		this.parent = parent;
		this.nameOrHandle = nameOrHandle;
	}

	@Override
	protected Iterable<WebElementsDriver<T>> webDrivers() {
		final WebElementsDriver<T> wd = rootWebDriver();
		final String currentHandle = wd.getWindowHandle();
		return FluentIterable.from(wd.getWindowHandles()).filter(new Predicate<String>() {
			@Override
			public boolean apply(@Nullable String input) {
				return !StringUtils.equals(currentHandle, input);
			}
		}).transform(new Function<String, WebElementsDriver<T>>() {
			@Override
			@Nullable
			public WebElementsDriver<T> apply(@Nullable String input) {
				System.out.println(format("Creating window driver for %s", input));
				return new WindowWebElementsDriver<T>(wd, factory, input);
			}
		}).toImmutableList();		
	}

	@Override
	protected WebElementsDriver<T> rootWebDriver() {
		return parent.rootWebDriver();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof WindowWebElementsImpl) {
			WindowWebElementsImpl<T> elem = (WindowWebElementsImpl<T>) obj;
			return 
					Objects.equal(elem.parent, this.parent) && 
					Objects.equal(elem.nameOrHandle, this.nameOrHandle);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(parent, nameOrHandle);
	}
}
