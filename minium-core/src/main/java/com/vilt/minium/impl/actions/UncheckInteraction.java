package com.vilt.minium.impl.actions;

import com.vilt.minium.WebElements;

public class UncheckInteraction extends ClickInteraction {

	public UncheckInteraction(WebElements elems) {
		super(elems);
	}

	@Override
	protected void doPerform() {
		if (getSource().is(":checked")) {
			super.doPerform();
		}
	}
}
