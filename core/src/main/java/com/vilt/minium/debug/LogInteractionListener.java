package com.vilt.minium.debug;

import com.vilt.minium.actions.AfterFailInteractionEvent;
import com.vilt.minium.actions.AfterSuccessInteractionEvent;
import com.vilt.minium.actions.DefaultInteractionListener;

/**
 * The listener interface for receiving logInteraction events.
 * The class that is interested in processing a logInteraction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addLogInteractionListener<code> method. When
 * the logInteraction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see LogInteractionEvent
 */
public class LogInteractionListener extends DefaultInteractionListener {
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteractionListener#onAfterInteractingEvent(com.vilt.minium.actions.InteractionEvent)
	 */
	@Override
	protected void onAfterSuccessEvent(AfterSuccessInteractionEvent event) {
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteractionListener#onAfterFailingEvent(com.vilt.minium.actions.InteractionEvent)
	 */
	@Override
	protected void onAfterFailEvent(AfterFailInteractionEvent event) {
	}

}
