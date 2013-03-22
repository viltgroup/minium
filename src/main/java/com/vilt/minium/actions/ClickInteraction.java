package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class ClickInteraction extends MouseInteraction {

	ClickInteraction(WebElements source) {
		super(source);
	}

	@Override
	protected void doPerform() {
		getFirstElement().click();
	}
}
