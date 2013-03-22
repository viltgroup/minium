package com.vilt.minium.actions;

import org.openqa.selenium.support.ui.Select;

import com.vilt.minium.WebElements;

public class SelectionInteraction extends DefaultInteraction {

	public SelectionInteraction(WebElements source) {
		super(source);
	}
	
	protected Select getSelectElement() {
		return new Select(getFirstElement());
	}

}