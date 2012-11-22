package com.vilt.minium.impl.elements;

import static java.lang.String.format;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.driver.impl.WindowWebElementsDriver;
import com.vilt.minium.impl.WebElementsFactory;

public class WindowWebElementsImpl<T extends WebElements> extends BaseRootWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parent;
	private String nameOrHandle;
//	private T filter;

//	@SuppressWarnings("unchecked")
	public void init(WebElementsFactory factory, BaseWebElementsImpl<T> parent, String expr) {
		super.init(factory);
		this.parent = parent;
//		this.filter = ((TargetLocatorWebElements<JQueryWebElements<T>>) parent).window().find(expr);
	}

	public void init(WebElementsFactory factory, BaseWebElementsImpl<T> parent, T filter) {
		super.init(factory);
		this.parent = parent;
//		this.filter = filter;
	}

	@Override
//	@SuppressWarnings("unchecked")
	public Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		final WebElementsDriver<T> wd = rootWebDriver();
		final String currentHandle = wd.getWindowHandle();
		
//		if (filter != null) {
//			nameOrHandle = ((WebElementsDriverProvider<T>) filter).webDriver().getWindowHandle();
//		}
		
		Set<String> windowHandles;
		if (StringUtils.isNotEmpty(nameOrHandle)) {
			windowHandles = Sets.newHashSet(Collections.singleton(nameOrHandle));
		}
		else {
			windowHandles = Sets.newHashSet(wd.getWindowHandles());
		}

		windowHandles.remove(currentHandle);
		
		if (windowHandles.isEmpty()) {
			System.out.println("No different window found...");
			return Collections.emptyList();
		}
		else {
			return FluentIterable
					.from(windowHandles)
					.transform(new Function<String, WebElementsDriver<T>>() {
						@Override
						@Nullable
						public WebElementsDriver<T> apply(@Nullable String input) {
							System.out.println(format("Creating window driver for %s", input));
							return new WindowWebElementsDriver<T>(wd, factory, input);
						}
					}).toImmutableList();
		}
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
