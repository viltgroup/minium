/*
 * Copyright (C) 2013 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vilt.minium.actions;

import static com.vilt.minium.actions.Interactions.perform;
import static com.vilt.minium.actions.Interactions.performAndWait;
import static com.vilt.minium.actions.Interactions.with;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.Duration;
import com.vilt.minium.WebElements;
import com.vilt.minium.impl.tips.TipInteraction;
import com.vilt.minium.impl.tips.TipInteractionListener;

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
	 * @return the interaction performer
	 */
	public static InteractionPerformer withTip(String tip) {
		return with(tip(tip));
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
	
	public static void showTip(WebElements elements, String tip) {
		performAndWait(new TipInteraction(elements, tip));
	}
	
	public static void showTipAsync(WebElements elements, String tip) {
		perform(new TipInteraction(elements, tip));
	}
	
	public static void showTip(WebElements elements, String tip, long time, TimeUnit unit) {
		performAndWait(new TipInteraction(elements, tip, new Duration(time, unit)));
	}

	public static void showTipAsync(WebElements elements, String tip, long time, TimeUnit unit) {
		perform(new TipInteraction(elements, tip, new Duration(time, unit)));
	}
}