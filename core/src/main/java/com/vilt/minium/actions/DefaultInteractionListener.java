package com.vilt.minium.actions;

/**
 * The listener interface for receiving defaultInteraction events.
 * The class that is interested in processing a defaultInteraction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDefaultInteractionListener<code> method. When
 * the defaultInteraction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DefaultInteractionEvent
 */
public abstract class DefaultInteractionListener implements InteractionListener {

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.InteractionListener#onEvent(com.vilt.minium.actions.InteractionEvent)
	 */
	@Override
	public void onEvent(InteractionEvent event) {
		switch (event.getType()) {
		case BEFORE:
			onBeforeEvent((BeforeInteractionEvent) event);
			break;
		case AFTER_SUCCESS:
			onAfterSuccessEvent((AfterSuccessInteractionEvent) event);
			onAfterEvent((AfterInteractionEvent) event);
			break;
		case AFTER_FAIL:
			onAfterFailEvent((AfterFailInteractionEvent) event);
			onAfterEvent((AfterInteractionEvent) event);
			break;
		}
	}

	/**
	 * On before event.
	 *
	 * @param event the event
	 */
	protected void onBeforeEvent(BeforeInteractionEvent event) {
		// do nothing
	}
	
	/**
	 * On after event.
	 *
	 * @param event the event
	 */
	protected void onAfterEvent(AfterInteractionEvent event) {
		// do nothing
	}
	
	/**
	 * On after interacting event.
	 *
	 * @param event the event
	 */
	protected void onAfterSuccessEvent(AfterSuccessInteractionEvent event) {
		// do nothing
	}

	/**
	 * On after failing event.
	 *
	 * @param event the event
	 */
	protected void onAfterFailEvent(AfterFailInteractionEvent event) {
		// do nothing
	}
}
