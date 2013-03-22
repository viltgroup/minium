package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class SelectValInteraction extends SelectionInteraction {

	private String val;

	SelectValInteraction(WebElements source, String val) {
		super(source);
		this.val = val;
	}

	@Override
	protected void doPerform() {
		getSelectElement().selectByValue(val);
	}
}
