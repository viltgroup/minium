package com.vilt.minium.actions.touch;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.WrapsDriver;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.DefaultInteraction;

/**
 * The Class TouchInteraction.
 */
public abstract class TouchInteraction extends DefaultInteraction {

	/**
	 * Instantiates a new touch interaction.
	 *
	 * @param elems the elems
	 */
	public TouchInteraction(WebElements elems) {
		super(elems);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#getActions()
	 */
	@Override
	protected TouchActions getActions() {
		return newActions(getFirstElement(getSource()));
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#newActions(org.openqa.selenium.WebElement)
	 */
	@Override
	protected TouchActions newActions(WebElement elem) {
		return new TouchActions(((WrapsDriver) elem).getWrappedDriver());
	}
}
