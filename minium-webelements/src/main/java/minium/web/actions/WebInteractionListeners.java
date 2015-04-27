/*
 * Copyright (C) 2015 The Minium Authors
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
package minium.web.actions;

import minium.web.WebElements;
import minium.web.internal.actions.DefaultEnsureInteractionListener;
import minium.web.internal.actions.DefaultOnExceptionInteractionListener;
import minium.web.internal.actions.DefaultOnStaleElementReferenceInteractionListener;
import minium.web.internal.actions.DefaultOnTimeoutInteractionListener;
import minium.web.internal.actions.DefaultOnUnhandledAlertInteractionListener;

public class WebInteractionListeners {

    public static OnUnhandledAlertInteractionListener onUnhandledAlert() {
        return new DefaultOnUnhandledAlertInteractionListener();
    }

    public static OnTimeoutInteractionListener onTimeout() {
        return new DefaultOnTimeoutInteractionListener();
    }

    public static OnExceptionInteractionListener onStaleElementReference() {
        return new DefaultOnStaleElementReferenceInteractionListener();
    }

    public static OnExceptionInteractionListener onException() {
        return onException(Exception.class);
    }

    public static EnsureInteractionListener ensureExistence(WebElements elems) {
        return new DefaultEnsureInteractionListener(elems, true);
    }

    public static EnsureInteractionListener ensureUnexistence(WebElements elems) {
        return new DefaultEnsureInteractionListener(elems, false);
    }

    @SuppressWarnings("unchecked")
    public static OnExceptionInteractionListener onException(Class<? extends Throwable> clazz) {
        return new DefaultOnExceptionInteractionListener(clazz);
    }
}
