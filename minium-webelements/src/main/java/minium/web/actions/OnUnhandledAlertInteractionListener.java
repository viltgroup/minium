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

/**
 * In case of an unhandled alert occurs, this listener will be able to either accept or
 * dismiss the alert.
 *
 * <pre><code class="js">
 * browser.configure()
 *   .interactionListeners()
 *     .add(minium.interactionListeners
 *       .onUnhandledAlert() // on unhandled alert
 *       .accept()           // accept (or dismiss())
 *       .thenRetry()        // then retry
 *     )
 *   .done();
 * </code></pre>
 *
 * If you omit {@code accept()} / {@code dismiss()}, it will accept the alert by default.
 *
 * @author rui.figueira
 *
 */
public interface OnUnhandledAlertInteractionListener extends OnExceptionInteractionListener {
    public OnUnhandledAlertInteractionListener accept();
    public OnUnhandledAlertInteractionListener dismiss();
}
