package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilWindowClosed;

import com.vilt.minium.TimeoutException;
import com.vilt.minium.WebElements;

/**
 * The Class WaitWindowClosedElementsInteraction.
 */
public class WaitWindowClosedElementsInteraction extends WaitInteraction {

	/**
	 * Instantiates a new wait window closed elements interaction.
	 *
	 * @param elems the elems
	 */
	public WaitWindowClosedElementsInteraction(WebElements elems) {
		super(elems);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() throws TimeoutException {
		getSource().wait(getTimeout(), untilWindowClosed());
	}
}
