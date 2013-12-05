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
package com.vilt.minium.tips;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.JQueryResources;
import com.vilt.minium.WebElements;

/**
 * The Interface TipsWebElements.
 */
@JQueryResources(
        value  = { "minium/js/jquery.qtip.min.js", "minium/js/jquery.ba-dotimeout.min.js", "minium/js/tip.js" },
        styles = { "minium/css/jquery.qtip.min.css", "minium/css/tip.css" })
public interface TipWebElements extends WebElements {

    /**
     * Show tip.
     *
     * @param tip the tip
     * @param time the time
     * @param timeUnit the time unit
     */
    void showTip(String tip, long time, TimeUnit timeUnit);

}
