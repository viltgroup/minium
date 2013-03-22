package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class DoubleClickInteraction extends MouseInteraction {

	DoubleClickInteraction(WebElements source) {
		super(source);
	}

	@Override
	protected void doPerform() {
		getActions().doubleClick(getFirstElement()).perform();
	}
}
