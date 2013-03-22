package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class MoveMouseInteraction extends MouseInteraction {

	private int xOffset;
	private int yOffset;

	MoveMouseInteraction(WebElements source, int xOffset, int yOffset) {
		super(source);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	@Override
	protected void doPerform() {
		getActions().moveToElement(getFirstElement(), xOffset, yOffset).perform();
	}
}
