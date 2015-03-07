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

import minium.Elements;

import java.util.EventObject;

public abstract class InteractionEvent extends EventObject {

    private static final long serialVersionUID = -1830111797395332704L;

    public static enum Type {
        BEFORE_WAIT,
        BEFORE,
        AFTER_SUCCESS,
        AFTER_FAIL
    }

    private final Interaction interaction;

    public InteractionEvent(Elements source, Interaction interaction) {
        super(source);
        this.interaction = interaction;
    }

    @Override
    public Elements getSource() {
        return (Elements) super.getSource();
    }

    public abstract Type getType();

    public Interaction getInteraction() {
        return interaction;
    }

}
