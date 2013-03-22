package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class SelectInteraction extends SelectionInteraction {

	private String text;

	SelectInteraction(WebElements source, String text) {
		super(source);
		this.text = text;
	}
	
	@Override
	protected void doPerform() {
		getSelectElement().selectByVisibleText(text);
	}

}
