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
import minium.actions.internal.AbstractInteractable;
import minium.web.actions.WebInteractable;

public class DefaultWebInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements WebInteractable<T> {

    @Override
    public T submit() {
        return perform(new SubmitInteraction(getSource()));
    }

    @Override
    public T check() {
        return perform(new CheckInteraction(getSource()));
    }

    @Override
    public T uncheck() {
        return perform(new UncheckInteraction(getSource()));
    }

    // select
    @Override
    public T select(String text) {
        return perform(new SelectInteraction(getSource(), text));
    }

    @Override
    public T deselect(String text) {
        return perform(new DeselectInteraction(getSource(), text));
    }

    @Override
    public T selectVal(String val) {
        return perform(new SelectValInteraction(getSource(), val));
    }

    @Override
    public T deselectVal(String val) {
        return perform(new DeselectValInteraction(getSource(), val));
    }

    @Override
    public T selectAll() {
        return perform(new SelectAllInteraction(getSource()));
    }

    @Override
    public T deselectAll() {
        return perform(new DeselectAllInteraction(getSource()));
    }

    @Override
    public T scrollIntoView() {
        return perform(new ScrollIntoViewInteraction(getSource()));
    }

    @Override
    public T close() {
        return perform(new CloseInteraction(getSource()));
    }
}
