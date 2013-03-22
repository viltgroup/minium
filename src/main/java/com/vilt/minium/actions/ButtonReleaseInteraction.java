package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class ButtonReleaseInteraction extends KeyboardInteraction {

	ButtonReleaseInteraction(WebElements source) {
		super(source);
	}
	
	@Override
	protected void doPerform() {
		getActions().release(getFirstElement()).perform();
	}

}
