package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class DeselectAllInteraction.
 */
public class DeselectAllInteraction extends SelectionInteraction {

	/**
	 * Instantiates a new deselect all interaction.
	 *
	 * @param source the source
	 */
	DeselectAllInteraction(WebElements source) {
		super(source);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getSelectElement().deselectAll();
	}
}
