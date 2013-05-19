package com.vilt.minium.actions.touch;

import com.vilt.minium.WebElements;

/**
 * The Class FlickInteraction.
 */
public class FlickInteraction extends TouchInteraction {

	/**
	 * Instantiates a new flick interaction.
	 *
	 * @param elements the elements
	 * @param xSpeed the x speed
	 * @param ySpeed the y speed
	 */
	public FlickInteraction(WebElements elements, int xSpeed, int ySpeed) {
		super(elements);
	}

	/**
	 * Instantiates a new flick interaction.
	 *
	 * @param elements the elements
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 * @param speed the speed
	 */
	public FlickInteraction(WebElements elements, int xOffset, int yOffset, int speed) {
		super(elements);
	}

	@Override
	protected void doPerform() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}
