package com.vilt.minium.tips;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.openqa.selenium.support.ui.Sleeper;

import com.vilt.minium.Duration;
import com.vilt.minium.WebElements;
import com.vilt.minium.actions.AsyncInteraction;
import com.vilt.minium.actions.DefaultInteraction;

public class TipInteraction extends DefaultInteraction implements AsyncInteraction {

	private String tip;
	private Duration duration;
	private long start = -1;

	public TipInteraction(WebElements elems, String tip, Duration duration) {
		super(elems);
		this.tip = tip;
		this.duration = duration;
	}

	public String getTip() {
		return tip;
	}
	
	@Override
	protected void doPerform() {
		start = System.currentTimeMillis();
		TipWebElements tipsElems = (TipWebElements) getSource();
		tipsElems.showTip(tip, duration.getTime(), duration.getUnit());
	}

	@Override
	public boolean isComplete() {
		return start < 0;
	}

	@Override
	public void waitUntilCompleted() {
		try {
			long end = duration.getUnit().convert(duration.getTime(), MILLISECONDS);
			long time = (start + end) - System.currentTimeMillis();
			
			if (time > 0) {
				Sleeper.SYSTEM_SLEEPER.sleep(new org.openqa.selenium.support.ui.Duration(time, MILLISECONDS));
			}
			
			start = -1;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
	
	

}
