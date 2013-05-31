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
package com.vilt.minium.speech;

import static com.vilt.minium.actions.Interactions.perform;

import java.util.Locale;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.actions.InteractionListener;

public class SpeechInteractions {
	
	private static WebElementsDriver<CoreWebElements<?>> speechWebDriver;

	public static InteractionListener speechForTips() {
		return speechForTips(speechWebDriver, null);
	}
	
	public static InteractionListener speechForTips(Locale locale) {
		return speechForTips(speechWebDriver, locale);
	}

	public static InteractionListener speechForTips(WebElementsDriver<CoreWebElements<?>> wd) {
		return speechForTips(wd, null);
	}

	public static InteractionListener speechForTips(WebElementsDriver<CoreWebElements<?>> wd, Locale locale) {
		return new GoogleSpeechTipInteractionListener(wd, locale);
	}
	
	public static void speak(Locale locale, String text) {
		GoogleSpeechInteraction interaction = speechWebDriver == null ? 
			new ServerSideGoogleSpeechInteraction(locale, text) : new ClientSideGoogleSpeechInteraction(speechWebDriver, locale, text);
		perform(interaction);
		interaction.waitUntilCompleted();
	}

	public static void speak(String text) {
		speak(null, text);
	}

	public static void speakWith(WebElementsDriver<CoreWebElements<?>> wd) {
		speechWebDriver = wd;
	}

}
