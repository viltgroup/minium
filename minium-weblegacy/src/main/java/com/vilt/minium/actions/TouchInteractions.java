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
package com.vilt.minium.actions;

import minium.Elements;
import minium.actions.TouchInteractionPerformer;
import minium.web.internal.actions.touch.WebTouchInteractionPerformer;

/**
 * The Class TouchInteractions.
 */
public class TouchInteractions {

    // from org.openqa.selenium.interactions.touch.TouchActions
    /**
     * Single tap.
     *
     * @param elements the elements
     */
    public static void singleTap(Elements elements) {
        defaultPerformer().singleTap(elements);
    }

    /**
     * Down.
     *
     * @param elements the elements
     * @param x the x
     * @param y the y
     */
    public static void down(Elements elements, int x, int y) {
        defaultPerformer().down(elements, x, y);
    }

    /**
     * Up.
     *
     * @param elements the elements
     * @param x the x
     * @param y the y
     */
    public static void up(Elements elements, int x, int y) {
        defaultPerformer().up(elements, x, y);
    }

    /**
     * Move.
     *
     * @param elements the elements
     * @param x the x
     * @param y the y
     */
    public static void move(Elements elements, int x, int y) {
        defaultPerformer().move(elements, x, y);
    }

    /**
     * Double tap.
     *
     * @param elements the elements
     */
    public static void doubleTap(Elements elements) {
        defaultPerformer().doubleTap(elements);
    }

    /**
     * Long press.
     *
     * @param elements the elements
     */
    public static void longPress(Elements elements) {
        defaultPerformer().longPress(elements);
    }

    /**
     * Scroll.
     *
     * @param elements the elements
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public static void scroll(Elements elements, int xOffset, int yOffset) {
        defaultPerformer().scroll(elements, xOffset, yOffset);
    }

    /**
     * Flick.
     *
     * @param elements the elements
     * @param xSpeed the x speed
     * @param ySpeed the y speed
     */
    public static void flick(Elements elements, int xSpeed, int ySpeed) {
        defaultPerformer().flick(elements, xSpeed, ySpeed);
    }

    private static TouchInteractionPerformer defaultPerformer() {
        return new WebTouchInteractionPerformer();
    }
}
