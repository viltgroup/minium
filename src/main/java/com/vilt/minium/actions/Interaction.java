package com.vilt.minium.actions;


/**
 * The Interface Interaction.
 */
public interface Interaction {

	public void waitToPerform();
	
	/**
	 * Perform.
	 */
	public void perform();

	// listener registration
	/**
	 * Register listener.
	 *
	 * @param listener the listener
	 */
	public void registerListener(InteractionListener listener);
	
	/**
	 * Unregister listener.
	 *
	 * @param listener the listener
	 */
	public void unregisterListener(InteractionListener listener);
}
