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
package minium.web.internal.actions.touch;

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
    public T down(int x, int y) {
        perform(new TouchDownInteraction(getSource(), x, y));
        return myself();
    }

    @Override
    public T up(int x, int y) {
        perform(new TouchUpInteraction(getSource(), x, y));
        return myself();
    }

    @Override
    public T move(int x, int y) {
        perform(new TouchMoveInteraction(getSource(), x, y));
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
