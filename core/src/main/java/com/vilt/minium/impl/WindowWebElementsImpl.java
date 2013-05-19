package com.vilt.minium.impl;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.vilt.minium.JQueryWebElements;
import com.vilt.minium.TargetLocatorWebElements;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.WebElementsDriverProvider;
import com.vilt.minium.impl.utils.Casts;

public class WindowWebElementsImpl<T extends WebElements> extends DocumentRootWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parent;
	private String handle;
	private T filter;
	private boolean freeze;

	@SuppressWarnings("unchecked")
	public void init(WebElementsFactory factory, WebElements parent, String expr, boolean freeze) {
		super.init(factory);
		this.parent = Casts.<BaseWebElementsImpl<T>>cast(parent);
		this.filter = ((TargetLocatorWebElements<JQueryWebElements<T>>) parent).window().find(expr);
		this.freeze = freeze;
	}

	public void init(WebElementsFactory factory, WebElements parent, WebElements filter, boolean freeze) {
		super.init(factory);
		this.parent = Casts.<BaseWebElementsImpl<T>>cast(parent);
		this.filter = Casts.<T>cast(filter);
		this.freeze = freeze;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		final WebElementsDriver<T> wd = rootWebDriver();
		final String currentHandle = wd.getWindowHandle();
		
		if (filter != null) {
			Iterable<WebElementsDriver<T>> webDrivers = ((WebElementsDriverProvider<T>) filter).webDrivers();
			if (Iterables.size(webDrivers) == 1) {
				handle = Iterables.get(webDrivers, 0).getWindowHandle();
				if (freeze) {
					// this way wegN won't evaluate filter ever again, so we will keep using the
					// same window!
					filter = null;
				}
			}
		}
		else if (freeze && handle == null) {
			//we are going to try to capture a new window
			throw new UnsupportedOperationException("To be implemented...");
		}
		
		Set<String> windowHandles;
		if (StringUtils.isNotEmpty(handle)) {
			windowHandles = Sets.newHashSet(Collections.singleton(handle));
		}
		else {
			windowHandles = Sets.newHashSet(wd.getWindowHandles());
		}

		windowHandles.remove(currentHandle);
		
		if (windowHandles.isEmpty()) {
			return Collections.emptyList();
		}
		else {
			return FluentIterable
					.from(windowHandles)
					.transform(new Function<String, WebElementsDriver<T>>() {
						@Override
						@Nullable
						public WebElementsDriver<T> apply(@Nullable String input) {
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
	public T root(T filter, boolean freeze) {
		return parent.window(filter, freeze);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof WindowWebElementsImpl) {
			WindowWebElementsImpl<T> elem = (WindowWebElementsImpl<T>) obj;
			return 
					Objects.equal(elem.parent, this.parent) && 
					Objects.equal(elem.handle, this.handle);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(parent, handle);
	}
}
