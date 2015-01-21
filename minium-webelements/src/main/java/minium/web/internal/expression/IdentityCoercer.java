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
