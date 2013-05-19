package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class DragAndDropByInteraction.
 */
public class DragAndDropByInteraction extends MouseInteraction {

	private int xOffset;
	private int yOffset;

	/**
	 * Instantiates a new drag and drop by interaction.
	 *
	 * @param source the source
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	DragAndDropByInteraction(WebElements source, int xOffset, int yOffset) {
		super(source);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().dragAndDropBy(getFirstElement(), xOffset, yOffset).perform();
	}
}
