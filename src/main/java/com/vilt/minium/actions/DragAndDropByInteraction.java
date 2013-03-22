package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class DragAndDropByInteraction extends MouseInteraction {

	private int xOffset;
	private int yOffset;

	DragAndDropByInteraction(WebElements source, int xOffset, int yOffset) {
		super(source);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	@Override
	protected void doPerform() {
		getActions().dragAndDropBy(getFirstElement(), xOffset, yOffset).perform();
	}
}
