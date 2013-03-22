package com.vilt.minium.jquery.tips;

import static com.vilt.minium.actions.Interactions.with;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.actions.InteractionListener;
import com.vilt.minium.actions.InteractionPerformer;

public class TipsInteractions {

	public static InteractionListener tip(String tip) {
		return new TipInteractionListener(tip);
	}

	public static InteractionPerformer withTip(String tip) {
		return with(tip(tip));
	}

	public static InteractionListener tip(String tip, long time, TimeUnit unit) {
		return new TipInteractionListener(tip, time, unit);
	}
	
	public static InteractionPerformer withTip(String tip, long time, TimeUnit unit) {
		return with(tip(tip, time, unit));
	}
}
