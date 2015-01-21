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
package com.vilt.minium.debug;

import java.util.concurrent.TimeUnit;

import minium.web.WebElements;

/**
 * The Interface DebugWebElements.
 *
 * @author Rui
 */
public interface DebugWebElements extends WebElements {

    /**
     * Highlight.
     */
    public void highlight();

    /**
     * Highlight.
     *
     * @param color the color
     */
    public void highlight(String color);

    /**
     * Highlight.
     *
     * @param time the time
     * @param units the units
     */
    public void highlight(long time, TimeUnit units);

    /**
     * Highlight.
     *
     * @param color the color
     * @param time the time
     * @param units the units
     */
    public void highlight(String color, long time, TimeUnit units);

    /**
     * Highlight and count.
     *
     * @return the int
     */
    public int highlightAndCount();

}
