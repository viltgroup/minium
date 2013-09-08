package com.vilt.minium.impl.actions;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.ScrollWebElements;

public class ScrollIntoViewInteraction extends DefaultInteraction {

	public ScrollIntoViewInteraction(CoreWebElements<?> elems) {
		super(elems);
	}

	@Override
	protected void doPerform() {
		((ScrollWebElements) getSource()).scrollIntoView();
	}
}
