package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilNotEmpty;

import com.vilt.minium.WebElements;

public class WaitFoElementsInteraction extends WaitInteraction {

	public WaitFoElementsInteraction(WebElements elems) {
		super(elems);
	}
	
	@Override
	protected void doPerform() {
		getSource().wait(untilNotEmpty());
	}
}
