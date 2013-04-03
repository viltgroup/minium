package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class ClickAndHoldInteraction.
 */
public class ClickAndHoldInteraction extends MouseInteraction {

	/**
	 * Instantiates a new click and hold interaction.
	 *
	 * @param source the source
	 */
	ClickAndHoldInteraction(WebElements source) {
		super(source);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().clickAndHold(getFirstElement()).perform();
	}

}
