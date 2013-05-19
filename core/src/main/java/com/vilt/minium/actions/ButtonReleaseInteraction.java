package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class ButtonReleaseInteraction.
 */
public class ButtonReleaseInteraction extends KeyboardInteraction {

	/**
	 * Instantiates a new button release interaction.
	 *
	 * @param source the source
	 */
	ButtonReleaseInteraction(WebElements source) {
		super(source);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().release(getFirstElement()).perform();
	}

}
