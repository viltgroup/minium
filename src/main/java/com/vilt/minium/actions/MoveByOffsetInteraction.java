package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class MoveByOffsetInteraction extends MouseInteraction {

	private int xOffset;
	private int yOffset;

	MoveByOffsetInteraction(WebElements source, int xOffset, int yOffset) {
		super(source);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	@Override
	protected void doPerform() {
		getActions().moveByOffset(xOffset, yOffset).perform();
	}
}
