package com.vilt.minium.actions;

import org.openqa.selenium.WebElement;

import com.vilt.minium.WebElements;

/**
 * The Class FillInteraction.
 */
public class FillInteraction extends KeyboardInteraction {

	private CharSequence text;

	/**
	 * Instantiates a new fill interaction.
	 *
	 * @param source the source
	 * @param text the text
	 */
	FillInteraction(WebElements source, CharSequence text) {
		super(source);
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		WebElement elem = getFirstElement();
		elem.clear();
		elem.sendKeys(text);
	}
}
