package com.vilt.minium.actions;

import java.util.EventObject;

import com.vilt.minium.WebElements;

public class InteractionEvent extends EventObject {

	private static final long serialVersionUID = -1830111797395332704L;

	public static enum Type {
		BEFORE, AFTER, AFTER_FAILING
	}
	
	private Interaction interaction;
	private Type type;

	public InteractionEvent(WebElements source, Interaction interaction, Type type) {
		super(source);
		this.type = type;
		this.interaction = interaction;
	}
	
	@Override
	public WebElements getSource() {
		return (WebElements) super.getSource();
	}
	
	public Type getType() {
		return type;
	}
	
	public Interaction getInteraction() {
		return interaction;
	}
}
