package com.vilt.minium.tips;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Sleeper;

import com.vilt.minium.actions.DefaultInteractionListener;
import com.vilt.minium.actions.Interaction;
import com.vilt.minium.actions.InteractionEvent;
import com.vilt.minium.actions.MouseInteraction;
import com.vilt.minium.actions.SelectionInteraction;

public class TipInteractionListener extends DefaultInteractionListener {

	private String tip;
	private long time;
	private TimeUnit timeUnit;

	public TipInteractionListener(String tip) {
		this(tip, 2, SECONDS);
	}
	
	public TipInteractionListener(String tip, long time, TimeUnit timeUnit) {
		this.tip = tip;
		this.time = time;
		this.timeUnit = timeUnit;
	}
	
	@Override
	protected void onBeforeEvent(InteractionEvent event) {
		TipsWebElements tipsElems = (TipsWebElements) event.getSource();
		tipsElems.showTip(tip, time, timeUnit);

		if (waitBefore(event.getInteraction())) {
			waitTime(time, timeUnit);
		}
	}

	@Override
	protected void onAfterEvent(InteractionEvent event) {
		if (!waitBefore(event.getInteraction())) {
			waitTime(time, timeUnit);
		}
	}
	
	protected boolean waitBefore(Interaction interaction) {
		return interaction instanceof MouseInteraction || interaction instanceof SelectionInteraction;
	}
	
	protected void waitTime(long time, TimeUnit unit) {
		Duration duration = new Duration(time, unit);
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(duration);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
}
