package com.vilt.minium.tips;

import static com.vilt.minium.actions.Interactions.perform;
import static com.vilt.minium.actions.Interactions.with;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.Duration;
import com.vilt.minium.WebElements;
import com.vilt.minium.actions.InteractionListener;
import com.vilt.minium.actions.InteractionPerformer;

/**
 * The Class TipsInteractions.
 */
public class TipInteractions {

	/**
	 * Tip.
	 *
	 * @param tip the tip
	 * @return the interaction listener
	 */
	public static InteractionListener tip(String tip) {
		return new TipInteractionListener(tip);
	}

	/**
	 * With tip.
	 *
	 * @param tip the tip
	 * @return the interaction performer
	 */
	public static InteractionPerformer withTip(String tip) {
		return with(tip(tip));
	}

	/**
	 * Tip.
	 *
	 * @param tip the tip
	 * @param time the time
	 * @param unit the unit
	 * @return the interaction listener
	 */
	public static InteractionListener tip(String tip, long time, TimeUnit unit) {
		return new TipInteractionListener(tip, new Duration(time, unit));
	}
	
	/**
	 * With tip.
	 *
	 * @param tip the tip
	 * @param time the time
	 * @param unit the unit
	 * @return the interaction performer
	 */
	public static InteractionPerformer withTip(String tip, long time, TimeUnit unit) {
		return with(tip(tip, time, unit));
	}
	
	public static void showTip(WebElements elements, String tip, long time, TimeUnit unit) {
		perform(new TipInteraction(elements, tip, new Duration(time, unit)));
	}
}
