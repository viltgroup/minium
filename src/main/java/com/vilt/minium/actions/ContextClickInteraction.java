package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class ContextClickInteraction extends MouseInteraction {

	ContextClickInteraction(WebElements source) {
		super(source);
	}

	@Override
	protected void doPerform() {
		getActions().contextClick(getFirstElement()).perform();
	}
}
