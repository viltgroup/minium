package com.vilt.minium.actions;

import java.util.EventObject;

import com.vilt.minium.WebElements;

/**
 * The Class InteractionEvent.
 */
public abstract class InteractionEvent extends EventObject {

	private static final long serialVersionUID = -1830111797395332704L;

	/**
	 * The Enum Type.
	 */
	public static enum Type {
		
		/** The before. */
		BEFORE, 
		/** The after. */
		AFTER_SUCCESS,
		/** The after failing. */
		AFTER_FAIL
	}
	
	private Interaction interaction;

	/**
	 * Instantiates a new interaction event.
	 *
	 * @param source the source
	 * @param interaction the interaction
	 * @param type the type
	 */
	public InteractionEvent(WebElements source, Interaction interaction) {
		super(source);
		this.interaction = interaction;
	}
	
	/* (non-Javadoc)
	 * @see java.util.EventObject#getSource()
	 */
	@Override
	public WebElements getSource() {
		return (WebElements) super.getSource();
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public abstract Type getType();
	
	/**
	 * Gets the interaction.
	 *
	 * @return the interaction
	 */
	public Interaction getInteraction() {
		return interaction;
	}
}
