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

import static java.lang.String.format;

import javax.annotation.Nullable;

import org.openqa.selenium.WebElement;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.vilt.minium.WaitWebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.WebElementsDriverProvider;

public class WaitPredicates {

    /**
     * Predicate to use with {@link WaitWebElements#wait(Predicate)} methods which ensures that
     * evaluation will only be successful when this instance is empty (that is, evaluates
     * to zero {@link org.openqa.selenium.WebElement} instances).
     *
     * @param <T> the generic type
     * @return predicate that returns true if it is empty
     */
    public static <T extends WaitWebElements<?>> Predicate<T> whileNotEmpty() {
        return new Predicate<T>() {
            @Override
            public boolean apply(T input) {
                return Iterables.isEmpty(input);
            }

            @Override
            public String toString() {
                return "whileNotEmpty()";
            }
        };
    }

    /**
     * Predicate to use with {@link WaitWebElements#wait(Predicate)} methods which ensures that
     * evaluation will only be successful when this instance is not empty (that is, evaluates
     * to one or more {@link WebElement} instances.
     *
     * @param <T> the generic type
     * @return predicate that returns true if it is empty
     */
    public static <T extends WaitWebElements<?>> Predicate<T> whileEmpty() {
        return new Predicate<T>() {
            @Override
            public boolean apply(T input) {
                return !Iterables.isEmpty(input);
            }

            @Override
            public String toString() {
                return "whileEmpty()";
            }
        };
    }

    /**
     * Predicate to use with {@link WaitWebElements#wait(Predicate)} methods which ensures that
     * evaluation will only be successful when this instance has a specific size.
     *
     * @param <T> the generic type
     * @param size number of matched {@link WebElement} instances
     * @return predicate that returns true if it has the exact size
     */
    public static <T extends WaitWebElements<?>> Predicate<T> untilSize(final int size) {
        return new Predicate<T>() {
            @Override
            public boolean apply(T input) {
                return Iterables.size(input) == size;
            }
            @Override
            public String toString() {
                return format("untilSize(%d)", size);
            }
        };
    }

    /**
     * Until window closed.
     *
     * @param <T> the generic type
     * @return the predicate
     */
    public static <T extends WaitWebElements<?>> Predicate<T> untilWindowClosed() {
        return new Predicate<T>() {

            private WebElementsDriver<?> webDriver;

            @Override
            public boolean apply(@Nullable T elems) {
                WebElementsDriver<?> webDriver = getWebDriver(elems);
                return webDriver.isClosed();
            }

            protected WebElementsDriver<?> getWebDriver(T elems) {
                if (webDriver == null) {
                    webDriver = ((WebElementsDriverProvider<?>) elems).webDriver();
                }
                return webDriver;
            }

            @Override
            public String toString() {
                return "untilWindowClosed()";
            }
        };
    }
}
