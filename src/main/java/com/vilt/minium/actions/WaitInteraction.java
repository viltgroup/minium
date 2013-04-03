package com.vilt.minium.actions;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElements;

/**
 * The Class WaitInteraction.
 */
public abstract class WaitInteraction extends DefaultInteraction {

	/**
	 * Instantiates a new wait interaction.
	 *
	 * @param elems the elems
	 */
	public WaitInteraction(WebElements elems) {
		super(elems);
	}
	
	@Override
	public void waitToPerform() {
		// do nothing
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#getSource()
	 */
	@Override
	public CoreWebElements<?> getSource() {
		return (CoreWebElements<?>) super.getSource();
	}
}
