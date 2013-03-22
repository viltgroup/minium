package com.vilt.minium.actions;

import org.openqa.selenium.WebElement;

import com.vilt.minium.WebElements;

public class ClickAllInteraction extends MouseInteraction {

	ClickAllInteraction(WebElements source) {
		super(source);
	}

	@Override
	protected void doPerform() {
		for (WebElement elem : getSource()) {
			elem.click();
		}
	}
}
