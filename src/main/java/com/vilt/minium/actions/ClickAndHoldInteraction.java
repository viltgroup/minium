package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class ClickAndHoldInteraction extends MouseInteraction {

	ClickAndHoldInteraction(WebElements source) {
		super(source);
	}
	
	@Override
	protected void doPerform() {
		getActions().clickAndHold(getFirstElement()).perform();
	}

}
