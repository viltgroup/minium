package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class ClickInteraction.
 */
public class ClickInteraction extends MouseInteraction {

	/**
	 * Instantiates a new click interaction.
	 *
	 * @param source the source
	 */
	ClickInteraction(WebElements source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getFirstElement().click();
	}
}
