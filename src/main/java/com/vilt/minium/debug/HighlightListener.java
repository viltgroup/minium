package com.vilt.minium.debug;

import com.vilt.minium.actions.BeforeInteractionEvent;
import com.vilt.minium.actions.DefaultInteractionListener;

/**
 * The listener interface for receiving highlight events.
 * The class that is interested in processing a highlight
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addHighlightListener<code> method. When
 * the highlight event occurs, that object's appropriate
 * method is invoked.
 *
 * @see HighlightEvent
 */
public class HighlightListener extends DefaultInteractionListener {

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteractionListener#onBeforeEvent(com.vilt.minium.actions.BeforeInteractionEvent)
	 */
	@Override
	protected void onBeforeEvent(BeforeInteractionEvent event) {
		((DebugWebElements) event.getSource()).highlight();
	}
	
}
