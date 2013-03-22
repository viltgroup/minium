package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilWindowClosed;

import com.vilt.minium.WebElements;

public class WaitWindowClosedElementsInteraction extends WaitInteraction {

	public WaitWindowClosedElementsInteraction(WebElements elems) {
		super(elems);
	}

	@Override
	protected void doPerform() {
		getSource().wait(untilWindowClosed());
	}
}
