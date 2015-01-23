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
package minium.internal;

import minium.Elements;
import minium.IterableElements;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class WaitPredicates {

    public abstract static class WaitPredicate<T extends Elements> implements Predicate<T> {
        private T elems;

        public WaitPredicate(T elems) {
            this.elems = elems;
        }

        public T getElements() {
            return elems;
        }
    }

    /**
     * Predicate to use with {@link WaitWebElements#wait(Predicate)} methods which ensures that
     * evaluation will only be successful when this instance is empty (that is, evaluates
     * to zero {@link org.openqa.selenium.WebElement} instances).
     *
     * @param <T> the generic type
     * @return predicate that returns true if it is empty
     */
    public static <T extends Elements> WaitPredicate<T> whileNotEmpty(T elems) {
        return new WaitPredicate<T>(elems) {
            @Override
            public boolean apply(T input) {
                Preconditions.checkArgument(input == getElements());
                return Iterables.isEmpty(input.as(IterableElements.class));
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
    public static <T extends Elements> WaitPredicate<T> whileEmpty(T elems) {
        return new WaitPredicate<T>(elems) {
            @Override
            public boolean apply(T input) {
                Preconditions.checkArgument(input == getElements());
                return !Iterables.isEmpty(input.as(IterableElements.class));
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
    public static <T extends Elements> WaitPredicate<T> untilSize(T elems, final int size) {
        return new WaitPredicate<T>(elems) {
            @Override
            public boolean apply(T input) {
                Preconditions.checkArgument(input == getElements());
                return Iterables.size(input.as(IterableElements.class)) == size;
            }
        };
    }
}
