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

import minium.Elements;

/**
 * The Class FlickInteraction.
 */
public class FlickInteraction extends TouchInteraction {

    /**
     * Instantiates a new flick interaction.
     *
     * @param elements the elements
     * @param xSpeed the x speed
     * @param ySpeed the y speed
     */
    public FlickInteraction(Elements elements, int xSpeed, int ySpeed) {
        super(elements);
    }

    /**
     * Instantiates a new flick interaction.
     *
     * @param elements the elements
     * @param xOffset the x offset
     * @param yOffset the y offset
     * @param speed the speed
     */
    public FlickInteraction(Elements elements, int xOffset, int yOffset, int speed) {
        super(elements);
    }

    @Override
    protected void doPerform() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
