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
package minium.web.internal;

import minium.internal.Locator;
import minium.web.WebElements;

public class WebLocator<T extends WebElements> extends Locator<T> {

    public WebLocator(Class<T> intf) {
        super(intf);
    }

    public WebLocator(T root, Class<T> intf, Class<?> ... others) {
        super(root, intf, others);
    }

    public T cssSelector(String selector) {
        return selector(selector);
    }

    public T name(String name) {
        return selector(CssSelectors.attr("name", name));
    }

    public T className(String name) {
        return selector(CssSelectors.className(name));
    }

    public T tagName(String name) {
        return selector(CssSelectors.tagName(name));
    }
}
