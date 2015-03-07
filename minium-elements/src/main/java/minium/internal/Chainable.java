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

import minium.AsIs;
import platypus.Mixin;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

public class Chainable<T> extends Mixin.Impl implements AsIs {

    @SuppressWarnings("serial")
    private final TypeToken<T> typeVariableToken = new TypeToken<T>(getClass()) { };

    protected TypeToken<T> typeVariableToken() {
        return typeVariableToken;
    }

    @SuppressWarnings("unchecked")
    protected <C> TypeToken<C> typeTokenFor(Class<?> clazz) {
        return (TypeToken<C>) TypeToken.of(clazz).where(new TypeParameter<T>() { }, typeVariableToken);
    }

    protected T myself() {
        return this.as(typeVariableToken);
    }
}
