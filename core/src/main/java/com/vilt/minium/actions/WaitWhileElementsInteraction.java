package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilEmpty;

import com.vilt.minium.TimeoutException;
import com.vilt.minium.WebElements;

/**
 * The Class WaitWhileElementsInteraction.
 */
public class WaitWhileElementsInteraction extends WaitInteraction {

	/**
	 * Instantiates a new wait while elements interaction.
	 *
	 * @param elems the elems
	 */
	public WaitWhileElementsInteraction(WebElements elems) {
		super(elems);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() throws TimeoutException {
		getSource().wait(getTimeout(), untilEmpty());
	}
}
