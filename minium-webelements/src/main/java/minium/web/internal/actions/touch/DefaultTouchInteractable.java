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
package minium.web.internal.actions.touch;

import minium.Offsets.Offset;
import minium.actions.Interactable;
import minium.actions.TouchInteractable;
import minium.actions.internal.AbstractInteractable;

/**
 * The Class TouchInteractionPerformer.
 */
public class DefaultTouchInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements TouchInteractable<T> {

    @Override
    public T singleTap() {
        perform(new SingleTapInteraction(getSource()));
        return myself();
    }

    @Override
    public T down(Offset offset) {
        perform(new TouchDownInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T up(Offset offset) {
        perform(new TouchUpInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T move(Offset offset) {
        perform(new TouchMoveInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T doubleTap() {
        perform(new DoubleTapInteraction(getSource()));
        return myself();
    }

    @Override
    public T longPress() {
        perform(new LongPressInteraction(getSource()));
        return myself();
    }

    @Override
    public T flick(int xSpeed, int ySpeed) {
        perform(new FlickInteraction(getSource(), xSpeed, ySpeed));
        return myself();
    }

    @Override
    public T scroll(int xOffset, int yOffset) {
        return myself();
    }
}
