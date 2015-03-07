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
package minium.web.internal.actions;

import minium.actions.Interactable;
import minium.actions.KeyboardInteractable;
import minium.actions.Keys;
import minium.actions.internal.AbstractInteractable;

public class DefaultKeyboardInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements KeyboardInteractable<T> {

    @Override
    public T keyUp(Keys keys) {
        perform(new KeyUpInteraction(getSource(), keys));
        return myself();
    }

    @Override
    public T keyDown(Keys keys) {
        perform(new KeyDownInteraction(getSource(), keys));
        return myself();
    }

    @Override
    public T sendKeys(CharSequence keys) {
        sendKeys(new CharSequence[] { keys });
        return myself();
    }

    @Override
    public T sendKeys(CharSequence... keys) {
        perform(new SendKeysInteraction(getSource(), keys));
        return myself();
    }

    @Override
    public T clear() {
        perform(new ClearInteraction(getSource()));
        return myself();
    }

    @Override
    public T type(CharSequence text) {
        perform(new TypeInteraction(getSource(), text));
        return myself();
    }

    @Override
    public T fill(CharSequence text) {
        perform(new FillInteraction(getSource(), text));
        return myself();
    }
}
