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
package minium;

import platypus.Mixin;

import com.google.common.reflect.TypeToken;

/**
 * @see Mixin
 * @author rui.figueira
 */
public interface AsIs {

    public abstract boolean is(Class<?> clazz);

    public abstract boolean is(TypeToken<?> type);

    public abstract <T> T as(Class<T> clazz);

    public abstract <T> T as(TypeToken<T> type);

}
