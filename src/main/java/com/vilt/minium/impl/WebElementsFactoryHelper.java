package com.vilt.minium.impl;

import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.elements.BaseWebElementsImpl;
import com.vilt.minium.impl.elements.ExpressionWebElementsImpl;
import com.vilt.minium.impl.elements.IFrameWebElementsImpl;
import com.vilt.minium.impl.elements.RootWebElementsImpl;
import com.vilt.minium.impl.elements.WindowWebElementsImpl;

public class WebElementsFactoryHelper {

	@SuppressWarnings("unchecked")
	public static <T extends WebElements> T createExpressionWebElements(WebElementsFactory factory, T parent, String fnName, Object ... args) {
		ExpressionWebElementsImpl<T> webElements = factory.create(ExpressionWebElementsImpl.class);
		webElements.init(factory, parent, fnName, args);
		return (T) webElements;
	}
	
	public static <T extends WebElements> T createWindowWebElements(WebElementsFactory factory, BaseWebElementsImpl<T> parent) {
		return WebElementsFactoryHelper.<T>createWindowWebElements(factory, parent, (T) null, false);
	}


	@SuppressWarnings("unchecked")
	public static <T extends WebElements> T createWindowWebElements(WebElementsFactory factory, BaseWebElementsImpl<T> parent, String expr, boolean freeze) {
		WindowWebElementsImpl<T> webElements = factory.create(WindowWebElementsImpl.class);
		webElements.init(factory, parent, expr, freeze);
		return (T) webElements;
	}

	@SuppressWarnings("unchecked")
	public static <T extends WebElements> T createWindowWebElements(WebElementsFactory factory, BaseWebElementsImpl<T> parent, T filter, boolean freeze) {
		WindowWebElementsImpl<T> webElements = factory.create(WindowWebElementsImpl.class);
		webElements.init(factory, parent, filter, freeze);
		return (T) webElements;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends WebElements> T createIFrameWebElements(WebElementsFactory factory, BaseWebElementsImpl<T> parent) {
		IFrameWebElementsImpl<T> webElements = factory.create(IFrameWebElementsImpl.class);
		webElements.init(factory, (BaseWebElementsImpl<T>) parent);
		return (T) webElements;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends WebElements> T createRootWebElements(WebElementsFactory factory, WebElementsDriver<T> driver) {
		RootWebElementsImpl<T> webElements = factory.create(RootWebElementsImpl.class);
		webElements.init(factory, driver);
		return (T) webElements;
	}
}
