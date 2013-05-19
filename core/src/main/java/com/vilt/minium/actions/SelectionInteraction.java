package com.vilt.minium.actions;

import org.openqa.selenium.support.ui.Select;

import com.vilt.minium.WebElements;

/**
 * The Class SelectionInteraction.
 */
public abstract class SelectionInteraction extends DefaultInteraction {

	/**
	 * Instantiates a new selection interaction.
	 *
	 * @param source the source
	 */
	public SelectionInteraction(WebElements source) {
		super(source);
	}
	
	/**
	 * Gets the select element.
	 *
	 * @return the select element
	 */
	protected Select getSelectElement() {
		return new Select(getFirstElement());
	}

}