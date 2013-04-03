package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class BeforeInteractionEvent.
 */
public class BeforeInteractionEvent extends InteractionEvent {

	private static final long serialVersionUID = 4826758132095640515L;
	
	public BeforeInteractionEvent(WebElements source, Interaction interaction) {
		super(source, interaction);
	}
	
	@Override
	public Type getType() {
		return Type.BEFORE;
	}
}
