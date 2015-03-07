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
package minium.web.internal.actions;

import java.util.Set;

import minium.internal.HasElementsFactory;
import minium.web.WebElements;
import minium.web.internal.WebLocator;
import platypus.Mixin;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;

public interface HasWebLocator<T extends WebElements> {

    public WebLocator<T> locator();

    public static class Impl<T  extends WebElements> extends Mixin.Impl implements HasWebLocator<T> {

        private final Class<T> intf;
        private final Set<Class<?>> otherIntfs;

        public Impl(Class<T> intf, Set<Class<?>> otherIntfs) {
            this.intf = intf;
            this.otherIntfs = otherIntfs == null ? ImmutableSet.<Class<?>>of() : otherIntfs;
        }

        @Override
        public WebLocator<T> locator() {
            @SuppressWarnings("serial")
            TypeToken<T> typeToken = new TypeToken<T>(HasWebLocator.Impl.class) { };
            T root = this.as(HasElementsFactory.class).factory().createRoot().as(typeToken);
            return new WebLocator<T>(root, intf, otherIntfs.toArray(new Class<?>[otherIntfs.size()]));
        }
    }
}
