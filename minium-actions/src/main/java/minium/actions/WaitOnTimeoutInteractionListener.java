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
package minium.actions;

import minium.Elements;

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
 * <pre><code class="javascript">
 * var loading = $("#loading");
 * $(":root").configure()
 *   .interactionListeners()
 *     .add($.interactionListeners
 *       .waitOnTimeout()                // wait on timeout
 *       .when(loading)                  // when loading exists
 *       .forUnexistence(loading)        // until loading doesn't exist
 *       .withWaitingPreset("very-slow") // with a very slow waiting preset
 *     )
 *   .done();
 * </code></pre>
 *
 * If you omit {@code forExistence} / {@code forUnexistence} elements, it will use the {@code unless} / {@code when} elements
 * for the wait condition.
 *
 * @author rui.figueira
 *
 */
public interface WaitOnTimeoutInteractionListener extends InteractionListener {
    WaitOnTimeoutInteractionListener withWaitingPreset(String preset);
    WaitOnTimeoutInteractionListener when(Elements elems);
    WaitOnTimeoutInteractionListener unless(Elements elems);
    WaitOnTimeoutInteractionListener forExistence(Elements elems);
    WaitOnTimeoutInteractionListener forUnexistence(Elements elems);

}
