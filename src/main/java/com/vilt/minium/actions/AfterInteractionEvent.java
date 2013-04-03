package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class BeforeInteractionEvent.
 */
public abstract class AfterInteractionEvent extends InteractionEvent {

	private static final long serialVersionUID = 4826758132095640515L;
	
	public AfterInteractionEvent(WebElements source, Interaction interaction) {
		super(source, interaction);
	}
}
