package com.vilt.minium.impl;

import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;

public class WebElementsFactoryHelper {

	public static WebElements createExpressionWebElements(WebElementsFactory factory, WebElements parent, String fnName, Object ... args) {
		ExpressionWebElementsImpl<?> webElements = factory.create(ExpressionWebElementsImpl.class);
		webElements.init(factory, parent, fnName, args);
		return webElements;
	}
	
	public static WebElements createWindowWebElements(WebElementsFactory factory, WebElements parent) {
		return WebElementsFactoryHelper.createWindowWebElements(factory, parent, (WebElements) null, false);
	}

	public static WebElements createWindowWebElements(WebElementsFactory factory, WebElements parent, String expr, boolean freeze) {
		WindowWebElementsImpl<?> webElements = factory.create(WindowWebElementsImpl.class);
		webElements.init(factory, parent, expr, freeze);
		return webElements;
	}

	public static WebElements createWindowWebElements(WebElementsFactory factory, WebElements parent, WebElements filter, boolean freeze) {
		WindowWebElementsImpl<?> webElements = factory.create(WindowWebElementsImpl.class);
		webElements.init(factory, parent, filter, freeze);
		return webElements;
	}
	
	public static WebElements createIFrameWebElements(WebElementsFactory factory, WebElements parent) {
		FrameWebElementsImpl<?> webElements = factory.create(FrameWebElementsImpl.class);
		webElements.init(factory, parent);
		return webElements;
	}
	
	public static WebElements createRootWebElements(WebElementsFactory factory, WebElementsDriver<?> driver) {
		RootWebElementsImpl<?> webElements = factory.create(RootWebElementsImpl.class);
		webElements.init(factory, driver);
		return webElements;
	}
}
