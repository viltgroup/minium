package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class SendKeysInteraction extends KeyboardInteraction {

	private CharSequence[] keys;

	SendKeysInteraction(WebElements source, CharSequence ... keys) {
		super(source);
		this.keys = keys;
	}

	@Override
	protected void doPerform() {
		getFirstElement().sendKeys(keys);
	}
}
