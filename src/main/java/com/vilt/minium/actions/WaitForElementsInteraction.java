package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilNotEmpty;

import com.vilt.minium.WebElements;

public class WaitForElementsInteraction extends WaitInteraction {

	public WaitForElementsInteraction(WebElements elems) {
		super(elems);
	}
	
	@Override
	protected void doPerform() {
		getSource().wait(getTimeout(), untilNotEmpty());
	}
}
