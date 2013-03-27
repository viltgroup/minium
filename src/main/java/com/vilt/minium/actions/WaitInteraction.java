package com.vilt.minium.actions;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElements;

public abstract class WaitInteraction extends DefaultInteraction {

	public WaitInteraction(WebElements elems) {
		super(elems);
	}
	
	@Override
	public CoreWebElements<?> getSource() {
		return (CoreWebElements<?>) super.getSource();
	}
}
