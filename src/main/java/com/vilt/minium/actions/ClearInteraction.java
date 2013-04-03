package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class ClearInteraction.
 */
public class ClearInteraction extends KeyboardInteraction {
	
	/**
	 * Instantiates a new clear interaction.
	 *
	 * @param source the source
	 */
	ClearInteraction(WebElements source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getFirstElement().clear();
	}
}
