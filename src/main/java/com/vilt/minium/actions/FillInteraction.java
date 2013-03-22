package com.vilt.minium.actions;

import org.openqa.selenium.WebElement;

import com.vilt.minium.WebElements;

public class FillInteraction extends KeyboardInteraction {

	private CharSequence text;

	FillInteraction(WebElements source, CharSequence text) {
		super(source);
		this.text = text;
	}

	@Override
	protected void doPerform() {
		WebElement elem = getFirstElement();
		elem.clear();
		elem.sendKeys(text);
	}
}
