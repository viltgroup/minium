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

import minium.Elements;
import minium.Offsets.Offset;
import minium.actions.Interactable;
import minium.actions.MouseInteractable;
import minium.actions.internal.AbstractInteractable;

public class DefaultMouseInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements MouseInteractable<T> {

    @Override
    public T clickAndHold() {
        return clickAndHold(null);
    }

    @Override
    public T clickAndHold(Offset offset) {
        perform(new ClickAndHoldInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T release() {
        return release(null);
    }

    @Override
    public T release(Offset offset) {
        perform(new ButtonReleaseInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T click() {
        return click(null);
    }

    @Override
    public T click(Offset offset) {
        perform(new ClickInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T doubleClick() {
        return doubleClick(null);
    }

    @Override
    public T doubleClick(Offset offset) {
        perform(new DoubleClickInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T moveTo() {
        return moveTo(null);
    }

    @Override
    public T moveTo(Offset offset) {
        perform(new MoveMouseInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T contextClick() {
        return contextClick(null);
    }

    @Override
    public T contextClick(Offset offset) {
        perform(new ContextClickInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T dragAndDrop(Elements target) {
        perform(new DragAndDropInteraction(getSource(), null, target, null));
        return myself();
    }
}
