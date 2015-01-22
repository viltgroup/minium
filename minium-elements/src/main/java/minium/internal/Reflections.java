package minium.internal;

import java.lang.reflect.Method;

import com.google.common.base.Throwables;

public class Reflections {

    public static Method getDeclaredMethod(Class<?> clazz, String name, Class<?> ... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            throw Throwables.propagate(e);
        }
    }

}
