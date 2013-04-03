package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class ContextClickInteraction.
 */
public class ContextClickInteraction extends MouseInteraction {

	/**
	 * Instantiates a new context click interaction.
	 *
	 * @param source the source
	 */
	ContextClickInteraction(WebElements source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().contextClick(getFirstElement()).perform();
	}
}
