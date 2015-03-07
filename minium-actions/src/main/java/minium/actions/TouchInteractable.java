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
package minium.actions;

import minium.Offsets.Offset;

import com.google.common.annotations.Beta;

@Beta
public interface TouchInteractable<T extends Interactable<?>> extends Interactable<T> {

    // from org.openqa.selenium.interactions.touch.TouchActions
    public T singleTap();

    public T down(Offset offset);

    public T up(Offset offset);

    public T move(Offset offset);

    public T doubleTap();

    public T longPress();

    public T scroll(int xOffset, int yOffset);

    public T flick(int xSpeed, int ySpeed);

}