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
import java.util.Map;

import com.google.common.base.Defaults;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.primitives.Primitives;

public class PrimitiveTypeCoercer implements Coercer {

    static class BooleanCoercer implements Coercer {
        @Override
        public boolean handles(Object obj, Type type) {
            return Primitives.unwrap(clazzFor(type)) == Boolean.TYPE;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            if (obj instanceof Boolean) return obj;
            if (obj instanceof String) return Boolean.parseBoolean(obj.toString());
            if (obj instanceof Number) return ((Number) obj).doubleValue() != 0;
            return false;
        }
    }

    static class ByteCoercer implements Coercer {
        @Override
        public boolean handles(Object obj, Type type) {
            return Primitives.unwrap(clazzFor(type)) == Byte.TYPE;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            if (obj instanceof Boolean) return ((Boolean) obj).booleanValue() ? 1 : 0;
            if (obj instanceof String) return Byte.parseByte(obj.toString());
            if (obj instanceof Number) return ((Number) obj).byteValue();
            throw new CannotCoerceException("Cannot convert to byte");
        }
    }

    static class ShortCoercer implements Coercer {
        @Override
        public boolean handles(Object obj, Type type) {
            return Primitives.unwrap(clazzFor(type)) == Short.TYPE;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            if (obj instanceof Boolean) return ((Boolean) obj).booleanValue() ? 1 : 0;
            if (obj instanceof String) return Short.parseShort(obj.toString());
            if (obj instanceof Number) return ((Number) obj).shortValue();
            throw new CannotCoerceException("Cannot convert to short");
        }
    }

    static class IntCoercer implements Coercer {
        @Override
        public boolean handles(Object obj, Type type) {
            return Primitives.unwrap(clazzFor(type)) == Integer.TYPE;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            if (obj instanceof Boolean) return ((Boolean) obj).booleanValue() ? 1 : 0;
            if (obj instanceof String) return Integer.parseInt(obj.toString());
            if (obj instanceof Number) return ((Number) obj).intValue();
            throw new CannotCoerceException("Cannot convert to int");
        }
    }

    static class LongCoercer implements Coercer {
        @Override
        public boolean handles(Object obj, Type type) {
            return Primitives.unwrap(clazzFor(type)) == Long.TYPE;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            if (obj instanceof Boolean) return ((Boolean) obj).booleanValue() ? 1L : 0L;
            if (obj instanceof String) return Long.parseLong(obj.toString());
            if (obj instanceof Number) return ((Number) obj).longValue();
            throw new CannotCoerceException("Cannot convert to long");
        }
    }

    static class FloatCoercer implements Coercer {
        @Override
        public boolean handles(Object obj, Type type) {
            return Primitives.unwrap(clazzFor(type)) == Float.TYPE;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            if (obj instanceof Boolean) return ((Boolean) obj).booleanValue() ? 1f : 0f;
            if (obj instanceof String) return Float.parseFloat(obj.toString());
            if (obj instanceof Number) return ((Number) obj).floatValue();
            throw new CannotCoerceException("Cannot convert to float");
        }
    }

    static class DoubleCoercer implements Coercer {
        @Override
        public boolean handles(Object obj, Type type) {
            return Primitives.unwrap(clazzFor(type)) == Double.TYPE;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            if (obj instanceof Boolean) return ((Boolean) obj).booleanValue() ? 1d : 0d;
            if (obj instanceof String) return Double.parseDouble(obj.toString());
            if (obj instanceof Number) return ((Number) obj).doubleValue();
            throw new CannotCoerceException("Cannot convert to double");
        }
    }

    static class CharCoercer implements Coercer {
        @Override
        public boolean handles(Object obj, Type type) {
            return Primitives.unwrap(clazzFor(type)) == Character.TYPE;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            if (obj instanceof Boolean) return ((Boolean) obj).booleanValue() ? 1d : 0d;
            if (obj instanceof String && obj.toString().length() == 1) return obj.toString().charAt(0);
            if (obj instanceof Number) return (char) ((Number) obj).intValue();
            throw new CannotCoerceException("Cannot convert to char");
        }
    }

    static Map<Class<?>, Coercer> primitiveCoercers = Maps.newHashMap();

    static {
        primitiveCoercers.put(Boolean.class, new BooleanCoercer());
        primitiveCoercers.put(Byte.class, new ByteCoercer());
        primitiveCoercers.put(Short.class, new ShortCoercer());
        primitiveCoercers.put(Integer.class, new IntCoercer());
        primitiveCoercers.put(Long.class, new LongCoercer());
        primitiveCoercers.put(Float.class, new FloatCoercer());
        primitiveCoercers.put(Double.class, new DoubleCoercer());
        primitiveCoercers.put(Character.class, new CharCoercer());
    }

    @Override
    public boolean handles(Object obj, Type type) {
        return clazzFor(type).isPrimitive() || Primitives.isWrapperType(clazzFor(type));
    }

    @Override
    public Object coerce(Object obj, Type type) {
        if (obj == null) return Defaults.defaultValue(clazzFor(type));

        Class<? extends Object> wrappedType = Primitives.wrap(clazzFor(type));
        Coercer coercer = primitiveCoercers.get(wrappedType);
        return coercer.coerce(obj, wrappedType);
    }

    protected static Class<?> clazzFor(Type type) {
        if (type instanceof Class) return (Class<?>) type;
        if (type instanceof ParameterizedType) return (Class<?>) ((ParameterizedType) type).getRawType();
        if (type instanceof TypeVariable) {
            Type[] bounds = ((TypeVariable<?>) type).getBounds();
            Preconditions.checkArgument(bounds.length < 2, "TypeVariable bounds only supported with 0 or 1 bound, and this one has %s", bounds.length);
            if (bounds.length == 0) return Object.class;
            return clazzFor(bounds[0]);
        }
        throw new IllegalArgumentException(format("type %s is not a class or parameterized type", type));
    }
}
