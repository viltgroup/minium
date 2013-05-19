package com.vilt.minium.actions;

import com.vilt.minium.Duration;

/**
 * The listener interface for receiving timeoutInteraction events.
 * The class that is interested in processing a timeoutInteraction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addTimeoutInteractionListener<code> method. When
 * the timeoutInteraction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see TimeoutInteractionEvent
 */
public class TimeoutInteractionListener extends DefaultInteractionListener {

	private Duration timeout;

	/**
	 * Instantiates a new timeout interaction listener.
	 *
	 * @param timeout the timeout
	 */
	public TimeoutInteractionListener(Duration timeout) {
		this.timeout = timeout;
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteractionListener#onBeforeEvent(com.vilt.minium.actions.InteractionEvent)
	 */
	@Override
	protected void onBeforeEvent(BeforeInteractionEvent event) {
		Interaction interaction = event.getInteraction();
		if (interaction instanceof DefaultInteraction) {
			DefaultInteraction defInteraction = (DefaultInteraction) interaction;
			if (defInteraction.getTimeout() == null) {
				defInteraction.setTimeout(timeout);
			}
		}
	}
}
