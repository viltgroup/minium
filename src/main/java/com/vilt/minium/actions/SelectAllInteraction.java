package com.vilt.minium.actions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.vilt.minium.WebElements;

public class SelectAllInteraction extends SelectionInteraction {

	SelectAllInteraction(WebElements source) {
		super(source);
	}

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
