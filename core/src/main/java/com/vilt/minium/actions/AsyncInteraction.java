package com.vilt.minium.actions;


/**
 * The Interface Interaction.
 */
public interface AsyncInteraction extends Interaction {
	
	public boolean isComplete();
	
	public void waitUntilCompleted();
}
