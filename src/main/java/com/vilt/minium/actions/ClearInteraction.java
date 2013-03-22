package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class ClearInteraction extends KeyboardInteraction {
	
	ClearInteraction(WebElements source) {
		super(source);
	}

	@Override
	protected void doPerform() {
		getFirstElement().clear();
	}
}
