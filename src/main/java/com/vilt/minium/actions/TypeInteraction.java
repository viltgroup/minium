package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class TypeInteraction extends KeyboardInteraction {

	private CharSequence text;

	TypeInteraction(WebElements source, CharSequence text) {
		super(source);
		this.text = text;
	}

	@Override
	protected void doPerform() {
		getFirstElement().sendKeys(text);
	}
}
