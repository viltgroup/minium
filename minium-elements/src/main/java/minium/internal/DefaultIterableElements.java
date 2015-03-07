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
package minium.internal;

import java.util.Iterator;

import minium.BasicElements;
import minium.Elements;
import minium.FreezableElements;
import minium.IterableElements;

import com.google.common.collect.AbstractIterator;
import com.google.common.reflect.TypeToken;

public class DefaultIterableElements<T extends Elements> extends BaseElements<T> implements IterableElements<T> {

    private final class ElementsIterator extends AbstractIterator<T> {
        private final Elements elems;
        private final int size;
        private int current;

        private ElementsIterator(Elements elems) {
            this.size = elems.as(BasicElements.class).size();
            this.elems = elems;
        }

        @Override
        protected T computeNext() {
            if (current == size) {
                return endOfData();
            }
            TypeToken<BasicElements<T>> typeToken = typeTokenFor(BasicElements.class);
            return elems.as(typeToken).eq(current++);
        }
    }

    @Override
    public Iterator<T> iterator() {
        final Elements elems = myself().is(FreezableElements.class) ? myself().as(FreezableElements.class).freeze() : myself();
        return new ElementsIterator(elems);
    }
}
