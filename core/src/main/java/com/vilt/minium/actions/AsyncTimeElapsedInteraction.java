package com.vilt.minium.actions;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.openqa.selenium.support.ui.Sleeper;

import com.vilt.minium.Duration;
import com.vilt.minium.WebElements;

public abstract class AsyncTimeElapsedInteraction extends DefaultInteraction implements AsyncInteraction {
	
	protected Duration duration;
	private long start = -1;
	
	public AsyncTimeElapsedInteraction(WebElements elems, Duration duration) {
		super(elems);
		this.duration = duration;
	}

	@Override
	protected void doPerform() {
		start = System.currentTimeMillis();
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
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		} finally {
			start = -1;
		}
	}
	
}