package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilEmpty;

import com.vilt.minium.WebElements;

public class WaitWhileElementsInteraction extends WaitInteraction {

	public WaitWhileElementsInteraction(WebElements elems) {
		super(elems);
	}

	@Override
	protected void doPerform() {
		getSource().wait(untilEmpty());
	}
}
