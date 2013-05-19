package com.vilt.minium.actions;

import org.openqa.selenium.Keys;

import com.vilt.minium.WebElements;

/**
 * The Class KeyDownInteraction.
 */
public class KeyDownInteraction extends KeyboardInteraction {

	private Keys keys;

	/**
	 * Instantiates a new key down interaction.
	 *
	 * @param source the source
	 * @param keys the keys
	 */
	KeyDownInteraction(WebElements source, Keys keys) {
		super(source);
		this.keys = keys;
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().keyDown(getFirstElement(), keys).perform();
	}

}
