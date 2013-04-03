package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilNotEmpty;

import com.vilt.minium.WebElements;

/**
 * The Class WaitForElementsInteraction.
 */
public class WaitForElementsInteraction extends WaitInteraction {

	/**
	 * Instantiates a new wait for elements interaction.
	 *
	 * @param elems the elems
	 */
	public WaitForElementsInteraction(WebElements elems) {
		super(elems);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getSource().wait(getTimeout(), untilNotEmpty());
	}
}
