package minium;

import java.lang.reflect.Method;

import platypus.MixinClass;
import platypus.MixinClasses;
import minium.internal.InternalFinder;
import minium.internal.Reflections;

import com.google.common.base.Preconditions;

public class Finder<T extends Elements> {

    private static final Method FIND_METHOD = Reflections.getDeclaredMethod(FindElements.class, "find", String.class);

    public static <E extends Elements> Finder<E> by(Class<E> clazz, Class<?> ... others) {
        return new Finder<E>(clazz, others);
    }

    private MixinClass<T> mixinClass;

    protected Finder(Class<T> intf, Class<?> ... others) {
        Preconditions.checkArgument(intf.isInterface(), "class %s is not an interface", intf);
        mixinClass = MixinClasses.builder(intf).addInterfaces(others).addInterfaces(InternalFinder.class).build();
    }

    public T root() {
        return InternalFinder.MethodInvocationImpl.createInternalFinder(mixinClass, null);
    }

    public T selector(String selector) {
        return createFinder(FIND_METHOD, selector);
    }

    protected T createFinder(Method method, Object ... args) {
        return InternalFinder.MethodInvocationImpl.createInternalFinder(mixinClass, null, method, args);
    }
}
