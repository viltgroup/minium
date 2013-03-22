package com.vilt.minium.actions;


public interface Interaction {

	public void perform();

	// listener registration
	public void registerListener(InteractionListener listener);
	public void unregisterListener(InteractionListener listener);
}
