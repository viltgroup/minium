package com.vilt.minium.impl.actions;

import com.vilt.minium.actions.AfterFailInteractionEvent;

public class RetryOnExceptionInteractionListener extends DefaultInteractionListener {
	
	@Override
	protected void onAfterFailEvent(AfterFailInteractionEvent event) {
		event.setRetry(true);
	}
}
