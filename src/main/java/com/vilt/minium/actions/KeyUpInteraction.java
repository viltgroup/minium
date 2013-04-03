package com.vilt.minium.actions;

import org.openqa.selenium.Keys;

import com.vilt.minium.WebElements;

/**
 * The Class KeyUpInteraction.
 */
public class KeyUpInteraction extends KeyboardInteraction {

	private Keys keys;

	/**
	 * Instantiates a new key up interaction.
	 *
	 * @param elements the elements
	 * @param keys the keys
	 */
	public KeyUpInteraction(WebElements elements, Keys keys) {
		super(elements);
		this.keys = keys;
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().keyUp(getFirstElement(), keys).perform();
	}
}
