package com.vilt.minium.actions;

import org.openqa.selenium.WebElement;

import com.vilt.minium.WebElements;

/**
 * The Class ClickAllInteraction.
 */
public class ClickAllInteraction extends MouseInteraction {

	/**
	 * Instantiates a new click all interaction.
	 *
	 * @param source the source
	 */
	ClickAllInteraction(WebElements source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		for (WebElement elem : getSource()) {
			elem.click();
		}
	}
}
