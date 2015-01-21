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

import minium.actions.InteractionListener;

import com.vilt.minium.speech.impl.GoogleSpeechInteraction;
import com.vilt.minium.speech.impl.GoogleSpeechTipInteractionListener;
import com.vilt.minium.speech.impl.ServerSideGoogleSpeechInteraction;

public class SpeechInteractions {

    public static InteractionListener speechForTips() {
        return speechForTips(null);
    }

    public static InteractionListener speechForTips(Locale locale) {
        return new GoogleSpeechTipInteractionListener(locale);
    }

    public static void speak(String text) {
        speak(null, text);
    }

    public static void speak(Locale locale, String text) {
        GoogleSpeechInteraction interaction = new ServerSideGoogleSpeechInteraction(locale, text);
        perform(interaction);
        interaction.waitUntilCompleted();
    }

}
