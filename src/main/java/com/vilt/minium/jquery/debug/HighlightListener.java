package com.vilt.minium.jquery.debug;

import com.vilt.minium.actions.DefaultInteractionListener;
import com.vilt.minium.actions.InteractionEvent;

public class HighlightListener extends DefaultInteractionListener {

	@Override
	protected void onBeforeEvent(InteractionEvent event) {
		((DebugWebElements) event.getSource()).highlight();
	}
	
}
