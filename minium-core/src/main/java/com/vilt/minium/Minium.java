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
package com.vilt.minium;

import org.openqa.selenium.WebDriver;

/**
 * Minium class makes the $ (dollar) method available, so that jQuery developers
 * can feel at home!
 *
 * @author Rui
 */
public class Minium {

    /**
     * The dollar 'function'. As in jQuery, this method is the entry point in
     * the {@link WebElements} API.
     *
     * <pre>
     * $(wd, &quot;:text&quot;).withLabel(&quot;Username&quot;).below(&quot;h2&quot;)
     * </pre>
     *
     *
     * @param <T> the generic type
     * @param wd the wd
     * @return the t
     */
    public static <T extends CoreWebElements<T>> T $(WebElementsDriver<T> wd) {
        return wd.webElements();
    }

    /**
     * The dollar 'function'. As in jQuery, this method is the entry point for
     * the {@link WebElements} API.
     *
     * <pre>
     * $(wd, &quot;:text&quot;).withLabel(&quot;Username&quot;).below(&quot;h2&quot;)
     * </pre>
     *
     * @param <T> the generic type
     * @param wd the wd
     * @param selector the selector
     * @return the t
     */
    public static <T extends CoreWebElements<T>> T $(WebElementsDriver<T> wd, String selector) {
        return wd.find(selector);
    }

    /**
     * The dollar 'function'. As in jQuery, this method is the entry point for
     * the {@link WebElements} API.
     *
     * @param wd the wd
     * @return the default web elements
     */
    public static DefaultWebElements $(WebDriver wd) {
        if (wd instanceof DefaultWebElementsDriver) {
            return $((DefaultWebElementsDriver) wd);
        }
        return $(new DefaultWebElementsDriver(wd));
    }

}
