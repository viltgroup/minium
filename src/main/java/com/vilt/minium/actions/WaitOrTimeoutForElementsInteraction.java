package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilNotEmpty;

import com.vilt.minium.WebElements;

public class WaitOrTimeoutForElementsInteraction extends WaitInteraction {

	public WaitOrTimeoutForElementsInteraction(WebElements elems) {
		super(elems);
	}
	
	@Override
	protected void doPerform() {
		getSource().waitOrTimeout(getTimeout(), untilNotEmpty());
	}
}
