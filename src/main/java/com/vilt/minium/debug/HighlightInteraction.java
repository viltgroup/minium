package com.vilt.minium.debug;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.DefaultInteraction;

public class HighlightInteraction extends DefaultInteraction {

	public HighlightInteraction(WebElements elems) {
		super(elems);
	}

	@Override
	protected void doPerform() {
		((DebugWebElements) getSource()).highlight();
	}
}
