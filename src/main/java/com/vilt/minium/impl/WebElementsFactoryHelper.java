package com.vilt.minium.impl;

import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.elements.FrameWebElementsImpl;
import com.vilt.minium.impl.elements.RootWebElementsImpl;
import com.vilt.minium.jquery.JQueryWebElements;

public class WebElementsFactoryHelper {
	
	public static <T extends JQueryWebElements<T>> RootWebElementsImpl<T> newRootWebElements(WebElementsDriver<T> wd) {
		RootWebElementsImpl<T> rootWebElements = wd.getFactory().create(wd, RootWebElementsImpl.class);
	}

}
