package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class DeselectValInteraction extends SelectionInteraction {

	private String val;

	DeselectValInteraction(WebElements source, String val) {
		super(source);
		this.val = val;
	}
	
	@Override
	protected void doPerform() {
		getSelectElement().deselectByValue(val);
	}

}
