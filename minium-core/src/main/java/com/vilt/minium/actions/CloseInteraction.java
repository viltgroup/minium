package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public class CloseInteraction extends DefaultInteraction {

	public CloseInteraction(WebElements elems) {
		super(elems);
	}

	@Override
	protected void doPerform() {
		getSource().close();
	}

}
