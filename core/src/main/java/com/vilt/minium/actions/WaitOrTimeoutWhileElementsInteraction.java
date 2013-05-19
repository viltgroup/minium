package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilEmpty;

import com.vilt.minium.WebElements;

/**
 * The Class WaitOrTimeoutWhileElementsInteraction.
 */
public class WaitOrTimeoutWhileElementsInteraction extends WaitInteraction {

	/**
	 * Instantiates a new wait or timeout while elements interaction.
	 *
	 * @param elems the elems
	 */
	public WaitOrTimeoutWhileElementsInteraction(WebElements elems) {
		super(elems);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getSource().waitOrTimeout(getTimeout(), untilEmpty());
	}
}
