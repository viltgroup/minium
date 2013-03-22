package com.vilt.minium.actions;

import org.openqa.selenium.Keys;

import com.vilt.minium.WebElements;

public class KeyUpInteraction extends KeyboardInteraction {

	private Keys keys;

	public KeyUpInteraction(WebElements elements, Keys keys) {
		super(elements);
		this.keys = keys;
	}

	@Override
	protected void doPerform() {
		getActions().keyUp(getFirstElement(), keys).perform();
	}
}
