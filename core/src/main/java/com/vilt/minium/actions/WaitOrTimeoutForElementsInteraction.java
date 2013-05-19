package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilNotEmpty;

import com.vilt.minium.WebElements;

/**
 * The Class WaitOrTimeoutForElementsInteraction.
 */
public class WaitOrTimeoutForElementsInteraction extends WaitInteraction {

	/**
	 * Instantiates a new wait or timeout for elements interaction.
	 *
	 * @param elems the elems
	 */
	public WaitOrTimeoutForElementsInteraction(WebElements elems) {
		super(elems);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getSource().waitOrTimeout(getTimeout(), untilNotEmpty());
	}
}
