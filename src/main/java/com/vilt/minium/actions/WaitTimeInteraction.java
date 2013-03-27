package com.vilt.minium.actions;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Sleeper;

import com.vilt.minium.Duration;

public class WaitTimeInteraction extends WaitInteraction {

	private Duration waitTime;

	public WaitTimeInteraction(Duration waitTime) {
		super(null);
		
		checkNotNull(waitTime);
		this.waitTime = waitTime;
	}

	@Override
	protected void doPerform() {
		long time = waitTime.getTime();
		TimeUnit unit = waitTime.getUnit();

		org.openqa.selenium.support.ui.Duration duration = new org.openqa.selenium.support.ui.Duration(time, unit);
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(duration);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
}
