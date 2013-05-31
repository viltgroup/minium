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

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.actions.Interactions.clear;
import static com.vilt.minium.actions.Interactions.click;
import static com.vilt.minium.actions.Interactions.waitForElements;
import static com.vilt.minium.actions.Interactions.waitTime;
import static com.vilt.minium.actions.Interactions.withTimeout;
import static com.vilt.minium.actions.Interactions.withoutWaiting;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Locale;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;

public class ClientSideGoogleSpeechInteraction extends GoogleSpeechInteraction {

	private WebElementsDriver<CoreWebElements<?>> wd;

	public ClientSideGoogleSpeechInteraction(WebElementsDriver<CoreWebElements<?>> wd, Locale locale, String text) {
		super(locale, text);
		this.wd = wd;
	}

	@Override
	public void waitToPerform() {
		if (withoutWaiting().checkEmpty($(wd, "#gt-appname").withText("Translate"))) {
			wd.get("http://translate.google.com/");
		}
		
		String displayLanguage = locale.getDisplayLanguage(Locale.ENGLISH);
		if (withoutWaiting().checkEmpty($(wd, "#gt-src-lang-sugg div").withAttr("aria-pressed", "true").withText(displayLanguage))) {
			CoreWebElements<?> srcItem = $(wd, ".goog-menuitem-content").withText(displayLanguage);

			waitTime(500, MILLISECONDS);
			click($(wd, "#gt-lang-src"));
			waitTime(500, MILLISECONDS);
			click(srcItem);
		}
	}

	@Override
	protected void doPerform() {
		CoreWebElements<?> source = $(wd, "#source");

		clear(source);
		source.call("val", text);
		waitTime(200, MILLISECONDS);

		click($(wd, "#gt-submit"));

		CoreWebElements<?> soundBtn = $(wd, "#gt-src-listen");
		CoreWebElements<?> listenNotPressed = soundBtn.filter(":not(.goog-toolbar-button-checked)").visible();

		waitForElements(listenNotPressed);
		click(listenNotPressed);
	}

	public boolean isComplete() {
		CoreWebElements<?> soundBtn = $(wd, "#gt-src-listen");

		CoreWebElements<?> listenPressed = soundBtn.filter(".goog-toolbar-button-checked").visible();
		return withoutWaiting().checkEmpty(listenPressed);
	}

	public void waitUntilCompleted() {
		CoreWebElements<?> soundBtn = $(wd, "#gt-src-listen");

		CoreWebElements<?> listenPressed = soundBtn.filter(".goog-toolbar-button-checked").visible();
		CoreWebElements<?> listenNotPressed = soundBtn.filter(":not(.goog-toolbar-button-checked)").visible();

		withTimeout(60, SECONDS).waitWhileElements(listenPressed);

		CoreWebElements<?> clear = $(wd, "#clear span").visible();

		if (withoutWaiting().checkNotEmpty(clear)) {
			click(clear);
			withTimeout(5, SECONDS).waitWhileElements(listenNotPressed);
		}
	}

}
