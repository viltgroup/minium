package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class SendKeysInteraction.
 */
public class SendKeysInteraction extends KeyboardInteraction {

	private CharSequence[] keys;

	/**
	 * Instantiates a new send keys interaction.
	 *
	 * @param source the source
	 * @param keys the keys
	 */
	SendKeysInteraction(WebElements source, CharSequence ... keys) {
		super(source);
		this.keys = keys;
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getFirstElement().sendKeys(keys);
	}
}
