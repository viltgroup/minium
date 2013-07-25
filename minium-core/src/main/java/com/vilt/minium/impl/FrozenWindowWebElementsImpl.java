package com.vilt.minium.impl;

import static com.google.common.collect.Iterables.size;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Iterables;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.WebElementsException;

public class FrozenWindowWebElementsImpl<T extends CoreWebElements<T>> extends WindowWebElementsImpl<T> {

	private WebElementsDriver<T> frozenWebElementsDriver;
	
	public void init(WebElementsFactory factory, T parent, String expr) {
		init(factory, parent, parent.window().find(expr));
	}
	
	@Override
	public Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		if (frozenWebElementsDriver == null) {
			String handle = null;
			Set<String> candidateHandles = super.candidateHandles();
			int size = size(candidateHandles);
			if (size == 1) {
				handle = Iterables.get(candidateHandles, 0);
			}
			else if (size > 1) {
				throw new WebElementsException("Cannot freeze window because more than one window matched");
			}

			if (StringUtils.isNotEmpty(handle)) {
				frozenWebElementsDriver = new WindowWebElementsDriver<T>(rootWebDriver(), factory, handle);
			}
		}
		
		if (frozenWebElementsDriver == null) {
			return Collections.emptyList();
		}
		
		return Collections.singletonList(frozenWebElementsDriver);
	}

}
