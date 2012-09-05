package com.vilt.minium.impl;

import static com.google.common.collect.FluentIterable.from;

import java.util.List;

import javax.annotation.Nullable;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.vilt.minium.driver.impl.FrameWebElementsDriver;

public abstract class RootFrameWebElementsImpl<T extends JQueryWebElementsImpl<T>> extends BaseComposedWebElementsImpl<T> {
	
	@Override
	protected List<WebElementsImpl<T>> getComposingElements() {
		return 
			from(getRootWebElements()).
			transform(new Function<WebElement, WebElementsImpl<T>>() {
					@Nullable
					public WebElementsImpl<T> apply(@Nullable WebElement input) {
						FrameWebElementsDriver<T> fwd = new FrameWebElementsDriver<T>(wd, factory, input);
						return (WebElementsImpl<T>) factory.create(fwd);
					}
			}).
			toImmutableList();
	}
}
