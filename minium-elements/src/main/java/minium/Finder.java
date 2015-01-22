package minium;

import java.lang.reflect.Method;

import minium.internal.InternalFinder;
import minium.internal.Reflections;

import com.google.common.base.Preconditions;

public class Finder<T extends Elements> {

    private static final Method FIND_METHOD = Reflections.getDeclaredMethod(FindElements.class, "find", String.class);

    public static <E extends Elements> Finder<E> by(Class<E> clazz) {
        return new Finder<E>(clazz);
    }

    private Class<T> intf;

    protected Finder(Class<T> intf) {
        Preconditions.checkArgument(intf.isInterface(), "class %s is not an interface", intf);
        this.intf = intf;
    }

    public T selector(String selector) {
        return InternalFinder.Impl.createInternalFinder(intf, null, FIND_METHOD, selector);
    }
}
