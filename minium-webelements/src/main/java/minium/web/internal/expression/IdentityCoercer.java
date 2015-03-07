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
package minium.web.internal.expression;

import static java.lang.String.format;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class IdentityCoercer implements Coercer {

    @Override
    public boolean handles(Object obj, Type type) {
        if (obj == null) return true;

        if (type instanceof Class) {
            return ((Class<?>) type).isInstance(obj);
        }
        if (type instanceof ParameterizedType) {
           return handles(obj, ((ParameterizedType) type).getRawType());
        }
        if (type instanceof TypeVariable) {
            for (Type boundType : ((TypeVariable<?>) type).getBounds()) {
                if (!handles(obj, boundType)) return false;
            }
            return true;
        }
        throw new IllegalStateException(format("Could not handle type %s", type));
    }

    @Override
    public Object coerce(Object obj, Type type) {
        return obj;
    }

}
