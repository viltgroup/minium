package com.vilt.minium.actions;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;

public class GetInteraction extends DefaultInteraction {

	private String url;

	public GetInteraction(WebElements elems, String url) {
		super(elems);
		this.url = url;
	}

	@Override
	protected void doPerform() {
		WebElementsDriver<?> webDriver = ((CoreWebElements<?>) getSource()).webDriver();
		webDriver.get(url);
	}
}
