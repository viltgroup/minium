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

import minium.Elements;

import com.google.common.reflect.TypeToken;

public abstract class BaseElements<T extends Elements> extends Chainable<T> implements Elements {

    protected InternalElementsFactory<T> internalFactory() {
        TypeToken<InternalElementsFactory<T>> factoryTypeToken = typeTokenFor(InternalElementsFactory.class);
        return this.as(HasElementsFactory.class).factory().as(factoryTypeToken);
    }

    protected Elements parent() {
        return this.is(HasParent.class) ? this.as(HasParent.class).parent() : null;
    }

    protected Elements root() {
        Elements curr = myself();
        while (curr.is(HasParent.class)) {
            curr = curr.as(HasParent.class).parent();
        }
        return curr;
    }
}