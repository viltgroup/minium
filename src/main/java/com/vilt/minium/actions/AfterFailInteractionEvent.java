package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class BeforeInteractionEvent.
 */
public class AfterFailInteractionEvent extends AfterInteractionEvent {

	private static final long serialVersionUID = 4826758132095640515L;
	private Throwable exception;
	private boolean retry = false;
	
	public AfterFailInteractionEvent(WebElements source, Interaction interaction, Throwable exception) {
		super(source, interaction);
		this.exception = exception;
	}
	
	@Override
	public Type getType() {
		return Type.AFTER_FAIL;
	}
	
	public Throwable getException() {
		return exception;
	}
	
	public boolean isRetry() {
		return retry;
	}
	
	public void setRetry(boolean retry) {
		this.retry = retry;
	}
}
