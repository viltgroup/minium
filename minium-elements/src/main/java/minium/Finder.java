package minium;

import java.lang.reflect.Method;

import minium.internal.InternalFinder;
import minium.internal.Reflections;
import platypus.MixinClass;
import platypus.MixinClasses;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class Finder<T extends Elements> {

    private static final Method FIND_METHOD = Reflections.getDeclaredMethod(FindElements.class, "find", String.class);

    private MixinClass<T> mixinClass;
    private transient Supplier<? extends T> rootSupplier;

    public Finder(T root, Class<T> intf, Class<?> ... others) {
        this(intf, others);
        setRoot(root);
    }

    public Finder(Class<T> intf, Class<?> ... others) {
        Preconditions.checkArgument(intf.isInterface(), "class %s is not an interface", intf);
        mixinClass = MixinClasses.builder(intf).addInterfaces(others).addInterfaces(InternalFinder.class).build();
    }

    @SuppressWarnings("unchecked")
    public Finder(T root) {
        this((Class<T>) root.getClass());
        setRoot(root);
    }

    public void setRoot(Supplier<? extends T> supplier) {
        this.rootSupplier = supplier;
    }

    public void setRoot(T root) {
        setRoot(Suppliers.ofInstance(root));
    }

    public T getRoot() {
        return rootSupplier == null ? null : rootSupplier.get();
    }

    public MixinClass<T> getMixinClass() {
        return mixinClass;
    }

    public void release() {
        rootSupplier = null;
    }

    public T root() {
        return InternalFinder.MethodInvocationImpl.createInternalFinder(this, null);
    }

    public T selector(String selector) {
        return createFinder(FIND_METHOD, selector);
    }

    protected T createFinder(Method method, Object ... args) {
        return InternalFinder.MethodInvocationImpl.createInternalFinder(this, null, method, args);
    }
}
