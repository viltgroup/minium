package com.vilt.minium.actions;

import java.util.EventListener;

/**
 * The listener interface for receiving interaction events.
 * The class that is interested in processing a interaction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addInteractionListener<code> method. When
 * the interaction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see InteractionEvent
 */
public interface InteractionListener extends EventListener {
	
	/**
	 * On event.
	 *
	 * @param event the event
	 */
	public void onEvent(InteractionEvent event);
}
