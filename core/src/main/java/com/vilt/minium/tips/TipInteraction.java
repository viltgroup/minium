package com.vilt.minium.tips;

import com.vilt.minium.Duration;
import com.vilt.minium.WebElements;
import com.vilt.minium.actions.AsyncTimeElapsedInteraction;

public class TipInteraction extends AsyncTimeElapsedInteraction {

	private String tip;

	public TipInteraction(WebElements elems, String tip, Duration duration) {
		super(elems, duration);
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}
	
	@Override
	protected void doPerform() {
		TipWebElements tipsElems = (TipWebElements) getSource();
		tipsElems.showTip(tip, duration.getTime(), duration.getUnit());
	}
}
