package com.vilt.minium.actions.touch;

import com.vilt.minium.WebElements;

/**
 * The Class SingleTapInteraction.
 */
public class SingleTapInteraction extends TouchInteraction {

	/**
	 * Instantiates a new single tap interaction.
	 *
	 * @param elems the elems
	 */
	public SingleTapInteraction(WebElements elems) {
		super(elems);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().singleTap(getFirstElement()).perform();
	}
}
