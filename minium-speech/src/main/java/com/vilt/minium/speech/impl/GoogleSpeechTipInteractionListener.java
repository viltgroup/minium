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
package com.vilt.minium.speech.impl;

import static com.vilt.minium.actions.Interactions.perform;

import java.util.Locale;

import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.impl.actions.AfterInteractionEvent;
import com.vilt.minium.impl.actions.BeforeInteractionEvent;
import com.vilt.minium.impl.actions.DefaultInteractionListener;
import com.vilt.minium.impl.tips.TipInteraction;

public class GoogleSpeechTipInteractionListener extends DefaultInteractionListener {

    private Locale locale;
    private GoogleSpeechInteraction speechInteraction;
    private WebElementsDriver<?> wd;

    public GoogleSpeechTipInteractionListener(WebElementsDriver<?> wd) {
        this(wd, null);
    }

    public GoogleSpeechTipInteractionListener(WebElementsDriver<?> wd, Locale locale) {
        this.wd = wd;
        this.locale = locale;
    }

    @Override
    protected void onBeforeEvent(BeforeInteractionEvent event) {
        if (event.getInteraction() instanceof TipInteraction) {
            TipInteraction tipInteraction = (TipInteraction) event.getInteraction();
            speechInteraction = wd == null ?
                new ServerSideGoogleSpeechInteraction(locale, tipInteraction.getTip()) :
                new ClientSideGoogleSpeechInteraction(wd, locale, tipInteraction.getTip());
            perform(speechInteraction);
        }
    }

    @Override
    protected void onAfterEvent(AfterInteractionEvent event) {
        if (event.getInteraction() instanceof TipInteraction) {
            speechInteraction.waitUntilCompleted();
        }
    }
}
