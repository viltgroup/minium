package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class DragAndDropInteraction extends MouseInteraction {

	private WebElements target;

	DragAndDropInteraction(WebElements source, WebElements target) {
		super(source);
		this.target = target;
	}

	@Override
	protected void doPerform() {
		getActions().dragAndDrop(getFirstElement(), getFirstElement(target)).perform();
	}
}
