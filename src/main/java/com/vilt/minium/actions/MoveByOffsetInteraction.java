package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class MoveByOffsetInteraction.
 */
public class MoveByOffsetInteraction extends MouseInteraction {

	private int xOffset;
	private int yOffset;

	/**
	 * Instantiates a new move by offset interaction.
	 *
	 * @param source the source
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	MoveByOffsetInteraction(WebElements source, int xOffset, int yOffset) {
		super(source);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().moveByOffset(xOffset, yOffset).perform();
	}
}
