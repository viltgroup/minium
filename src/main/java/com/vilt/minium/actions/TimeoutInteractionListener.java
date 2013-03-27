package com.vilt.minium.actions;

import com.vilt.minium.Duration;

public class TimeoutInteractionListener extends DefaultInteractionListener {

	private Duration timeout;

	public TimeoutInteractionListener(Duration timeout) {
		this.timeout = timeout;
	}
	
	@Override
	protected void onBeforeEvent(InteractionEvent event) {
		Interaction interaction = event.getInteraction();
		if (interaction instanceof DefaultInteraction) {
			DefaultInteraction defInteraction = (DefaultInteraction) interaction;
			if (defInteraction.getTimeout() == null) {
				defInteraction.setTimeout(timeout);
			}
		}
	}
}
