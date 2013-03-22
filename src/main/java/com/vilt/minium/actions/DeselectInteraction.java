package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class DeselectInteraction extends SelectionInteraction {

	private String text;

	DeselectInteraction(WebElements source, String text) {
		super(source);
		this.text = text;
	}
	
	@Override
	protected void doPerform() {
		getSelectElement().deselectByVisibleText(text);
	}
}
