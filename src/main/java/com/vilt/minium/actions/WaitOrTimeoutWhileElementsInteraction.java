package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilEmpty;

import com.vilt.minium.WebElements;

public class WaitOrTimeoutWhileElementsInteraction extends WaitInteraction {

	public WaitOrTimeoutWhileElementsInteraction(WebElements elems) {
		super(elems);
	}
	
	@Override
	protected void doPerform() {
		getSource().waitOrTimeout(getTimeout(), untilEmpty());
	}
}
