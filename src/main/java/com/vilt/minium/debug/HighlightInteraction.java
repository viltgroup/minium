package com.vilt.minium.debug;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.DefaultInteraction;

/**
 * The Class HighlightInteraction.
 */
public class HighlightInteraction extends DefaultInteraction {

	/**
	 * Instantiates a new highlight interaction.
	 *
	 * @param elems the elems
	 */
	public HighlightInteraction(WebElements elems) {
		super(elems);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		((DebugWebElements) getSource()).highlight();
	}
}
