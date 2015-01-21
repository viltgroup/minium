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

import java.util.EventObject;

/**
 * The Class InteractionEvent.
 */
public abstract class InteractionEvent extends EventObject {

    private static final long serialVersionUID = -1830111797395332704L;

    /**
     * The Enum Type.
     */
    public static enum Type {
        /** The before wait. */
        BEFORE_WAIT,
        /** The before. */
        BEFORE,
        /** The after. */
        AFTER_SUCCESS,
        /** The after failing. */
        AFTER_FAIL
    }

    private final Interaction interaction;

    /**
     * Instantiates a new interaction event.
     *
     * @param source the source
     * @param performer
     * @param interaction the interaction
     */
    public InteractionEvent(Elements source, Interaction interaction) {
        super(source);
        this.interaction = interaction;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.EventObject#getSource()
     */
    @Override
    public Elements getSource() {
        return (Elements) super.getSource();
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public abstract Type getType();

    /**
     * Gets the interaction.
     *
     * @return the interaction
     */
    public Interaction getInteraction() {
        return interaction;
    }

}
