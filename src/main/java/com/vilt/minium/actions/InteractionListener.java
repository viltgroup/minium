package com.vilt.minium.actions;

import java.util.EventListener;

public interface InteractionListener extends EventListener {
	public void onEvent(InteractionEvent event);
}
