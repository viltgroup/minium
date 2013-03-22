package com.vilt.minium.actions;

import org.openqa.selenium.Keys;

import com.vilt.minium.WebElements;

public class KeyDownInteraction extends KeyboardInteraction {

	private Keys keys;

	KeyDownInteraction(WebElements source, Keys keys) {
		super(source);
		this.keys = keys;
	}
	
	@Override
	protected void doPerform() {
		getActions().keyDown(getFirstElement(), keys).perform();
	}

}
