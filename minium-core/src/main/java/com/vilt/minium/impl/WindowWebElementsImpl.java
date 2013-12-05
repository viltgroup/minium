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
package com.vilt.minium.impl;

import static com.google.common.collect.FluentIterable.from;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.WebElementsDriverProvider;

public class WindowWebElementsImpl<T extends CoreWebElements<T>> extends DocumentRootWebElementsImpl<T> {

    private BaseWebElementsImpl<T> parentImpl;
    private T filter;

    @SuppressWarnings("unchecked")
    public void init(WebElementsFactory factory, T parent, T filter) {
        super.init(factory);
        this.parentImpl = (BaseWebElementsImpl<T>) parent;
        this.filter = filter;
    }

    @Override
    public Iterable<WebElementsDriver<T>> candidateWebDrivers() {
        Set<String> windowHandles = candidateHandles();

        if (windowHandles.isEmpty()) {
            return Collections.emptyList();
        } else {
            return FluentIterable.from(windowHandles).transform(new Function<String, WebElementsDriver<T>>() {
                @Override
                @Nullable
                public WebElementsDriver<T> apply(@Nullable String input) {
                    return new WindowWebElementsDriver<T>(rootWebDriver(), factory, input);
                }
            }).toList();
        }
    }

    @Override
    public WebElementsDriver<T> rootWebDriver() {
        return parentImpl.rootWebDriver();
    }

    @Override
    protected T root(T filter, boolean freeze) {
        return parentImpl.windows(filter, freeze);
    }

    @SuppressWarnings("unchecked")
    protected Set<String> candidateHandles() {
        final WebElementsDriver<T> wd = rootWebDriver();
        Set<String> windowHandles;

        if (filter != null) {
            Iterable<WebElementsDriver<T>> webDrivers = ((WebElementsDriverProvider<T>) filter).webDrivers();
            windowHandles = Sets.newHashSet(from(webDrivers).transform(new Function<WebElementsDriver<T>, String>() {
                @Override
                public String apply(WebElementsDriver<T> input) {
                    return input.getWindowHandle();
                }
            }));
        } else {
            windowHandles = Sets.newHashSet(wd.getWindowHandles());
        }

        return windowHandles;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj instanceof WindowWebElementsImpl) {
            WindowWebElementsImpl<T> elem = (WindowWebElementsImpl<T>) obj;
            return Objects.equal(elem.parentImpl, this.parentImpl) && Objects.equal(elem.filter, this.filter);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(parentImpl, filter);
    }

}
