package com.vilt.minium.impl.actions;

import com.vilt.minium.CoreWebElements;

public class SubmitInteraction extends DefaultInteraction {
	/**
	 * Instantiates a new clear interaction.
	 *
	 * @param source the source
	 */
	public SubmitInteraction(CoreWebElements<?> source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getFirstElement().submit();
	}

}
