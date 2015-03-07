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

import static java.lang.String.format;
import minium.internal.ElementsFactory.Builder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class Modules {

    public static Module<?> combine(final Iterable<? extends Module<?>> modules) {
        return new Module<Builder<?>>() {
            @Override
            @SuppressWarnings({ "unchecked", "rawtypes" })
            public void configure(Builder<?> builder) {
                for (Module module : modules) {
                    module.configure(builder);
                }
            }

            @Override
            public String toString() {
                return format("Module%s", Iterables.toString(modules));
            }
        };
    }

    public static Module<?> combine(final Module<?> ... modules) {
        return combine(ImmutableList.copyOf(modules));
    }
}
