package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class DoubleClickInteraction.
 */
public class DoubleClickInteraction extends MouseInteraction {

	/**
	 * Instantiates a new double click interaction.
	 *
	 * @param source the source
	 */
	DoubleClickInteraction(WebElements source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().doubleClick(getFirstElement()).perform();
	}
}
