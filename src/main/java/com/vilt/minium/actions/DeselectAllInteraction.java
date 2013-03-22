package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class DeselectAllInteraction extends SelectionInteraction {

	DeselectAllInteraction(WebElements source) {
		super(source);
	}
	
	@Override
	protected void doPerform() {
		getSelectElement().deselectAll();
	}
}
