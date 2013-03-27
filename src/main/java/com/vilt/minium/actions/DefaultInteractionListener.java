package com.vilt.minium.actions;

public abstract class DefaultInteractionListener implements InteractionListener {

	@Override
	public void onEvent(InteractionEvent event) {
		switch (event.getType()) {
		case BEFORE:
			onBeforeEvent(event);
			break;
		case AFTER:
			onAfterInteractingEvent(event);
			onAfterEvent(event);
			break;
		case AFTER_FAILING:
			onAfterFailingEvent(event);
			onAfterEvent(event);
			break;
		}
	}

	protected void onBeforeEvent(InteractionEvent event) {
		// do nothing
	}
	
	protected void onAfterEvent(InteractionEvent event) {
		// do nothing
	}
	
	protected void onAfterInteractingEvent(InteractionEvent event) {
		// do nothing
	}

	protected void onAfterFailingEvent(InteractionEvent event) {
		// do nothing
	}
}
