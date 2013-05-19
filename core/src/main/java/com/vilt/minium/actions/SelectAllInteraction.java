package com.vilt.minium.actions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.vilt.minium.WebElements;

/**
 * The Class SelectAllInteraction.
 */
public class SelectAllInteraction extends SelectionInteraction {

	/**
	 * Instantiates a new select all interaction.
	 *
	 * @param source the source
	 */
	SelectAllInteraction(WebElements source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		Select select = getSelectElement();
		if (!select.isMultiple()) {
			throw new UnsupportedOperationException("You may only deselect all options of a multi-select");
		}

		for (WebElement option : select.getOptions()) {
			if (!option.isSelected()) {
				option.click();
			}
		}
	}
}
