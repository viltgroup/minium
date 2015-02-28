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
package minium.web.actions;

import minium.Elements;
import minium.actions.TimeoutException;

/**
 * Let's say we're trying to click a botton, ensuring that no loading is in progress:
 *
 * <pre><code class="javascript">
 * $("button").unless("#loading").click();
 * </code></pre>
 *
 * Problem is that if loading is slow, it will probably end up with a {@link TimeoutException}.
 * To prevent it, without any extra code there, we can declare that when {@link TimeoutException}
 * occurs and {@code $("#loading")} exists, we'll wait until {@code $("#loading")} doesn't exist
 * anymore and only then retry the interaction.
 *
 * <pre><code class="js">
 * var loading = $("#loading");
 * browser.configure()
 *   .interactionListeners()
 *     .add(minium.interactionListeners
 *       .onTimeout()                    // on timeout
 *       .when(loading)                  // when loading exists
 *       .waitForUnexistence(loading)    // wait until loading doesn't exist anymore
 *       .withWaitingPreset("very-slow") // with a very slow waiting preset
 *       .thenRetry()                    // then retry
 *     )
 *   .done();
 * </code></pre>
 *
 * If you omit {@code waitForExistence} / {@code waitForUnexistence} elements, it will use the {@code unless} / {@code when} elements
 * for the wait condition.
 *
 * @author rui.figueira
 *
 */
public interface OnTimeoutInteractionListener extends OnExceptionInteractionListener {
    OnTimeoutInteractionListener when(Elements elems);
    OnTimeoutInteractionListener unless(Elements elems);
    OnTimeoutInteractionListener waitForExistence(Elements elems);
    OnTimeoutInteractionListener waitForUnexistence(Elements elems);
    OnTimeoutInteractionListener withWaitingPreset(String preset);
}
