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
import minium.actions.internal.TouchInteractionPerformer;
import minium.web.internal.actions.WebInteractionPerformer;

/**
 * The Class TouchInteractionPerformer.
 */
public class WebTouchInteractionPerformer extends WebInteractionPerformer implements TouchInteractionPerformer {

    // from org.openqa.selenium.interactions.touch.TouchActions
    /* (non-Javadoc)
     * @see com.vilt.minium.actions.touch.TouchInteractionPerformer#singleTap(minium.Elements)
     */
    @Override
    public void singleTap(Elements elements) {
        perform(new SingleTapInteraction(elements));
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.touch.TouchInteractionPerformer#down(minium.Elements, int, int)
     */
    @Override
    public void down(Elements elements, int x, int y) {
        perform(new TouchDownInteraction(elements, x, y));
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.touch.TouchInteractionPerformer#up(minium.Elements, int, int)
     */
    @Override
    public void up(Elements elements, int x, int y) {
        perform(new TouchUpInteraction(elements, x, y));
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.touch.TouchInteractionPerformer#move(minium.Elements, int, int)
     */
    @Override
    public void move(Elements elements, int x, int y) {
        perform(new TouchMoveInteraction(elements, x, y));
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.touch.TouchInteractionPerformer#doubleTap(minium.Elements)
     */
    @Override
    public void doubleTap(Elements elements) {
        perform(new DoubleTapInteraction(elements));
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.touch.TouchInteractionPerformer#longPress(minium.Elements)
     */
    @Override
    public void longPress(Elements elements) {
        perform(new LongPressInteraction(elements));
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.touch.TouchInteractionPerformer#flick(minium.Elements, int, int)
     */
    @Override
    public void flick(Elements elements, int xSpeed, int ySpeed) {
        perform(new FlickInteraction(elements, xSpeed, ySpeed));
    }

    @Override
    public void scroll(Elements elements, int xOffset, int yOffset) {
        // TODO Auto-generated method stub

    }

}
