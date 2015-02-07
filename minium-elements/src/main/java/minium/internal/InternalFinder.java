package minium.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

import minium.Elements;
import minium.Finder;
import platypus.AbstractMixinInitializer;
import platypus.InstanceProviders;
import platypus.Mixin;
import platypus.MixinClass;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

public interface InternalFinder extends Elements {

    public Elements eval(Elements elems);

    public abstract Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

    class Impl extends Mixin.Impl implements InternalFinder, InvocationHandler {

        protected final Finder<?> finder;
        protected final InternalFinder parent;

        public Impl(Finder<?> finder, InternalFinder parent) {
            this.finder = finder;
            this.parent = parent;
        }

        @Override
        public Elements eval(Elements elems) {
            return parent == null ? elems : parent.eval(elems);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<?> returnIntf = method.getReturnType();
            if (!Elements.class.isAssignableFrom(returnIntf)) {
                // let's check if Minium has some root set
                Elements root = finder.getRoot();
                Preconditions.checkState(root != null, "Method %s does not return an Elements class and Minium.get() does not return any root Elements", method);

                Elements elements = eval(root);
                return method.invoke(elements, args);
            }
            return createInternalFinder(finder, this, method, args);
        }

        public static <T> T createInternalFinder(final Finder<?> finder, InternalFinder parent) {
            final InternalFinder.Impl internalFinder = new InternalFinder.Impl(finder, parent);
            return doCreateInternalFinder(finder, internalFinder);
        }

        public static <T> T createInternalFinder(final Finder<?> finder, InternalFinder parent, Method method, Object ... args) {
            final InternalFinder.MethodInvocationImpl internalFinder = new InternalFinder.MethodInvocationImpl(finder, parent, method, args);
            return doCreateInternalFinder(finder, internalFinder);
        }

        private static <T> T doCreateInternalFinder(final Finder<?> finder, final InternalFinder.Impl internalFinder) {
            @SuppressWarnings("unchecked")
            final MixinClass<T> mixinClass = (MixinClass<T>) finder.getMixinClass();
            return mixinClass.newInstance(new AbstractMixinInitializer() {
                @Override
                protected void initialize() {
                    implement(Object.class, InternalFinder.class).with(InstanceProviders.ofInstance(internalFinder));
                    implementRemainers().with(InstanceProviders.adapt(internalFinder, mixinClass.getDeclaredInterfaces()));
                }
            });
        }
    }

    static class MethodInvocationImpl extends Impl implements InternalFinder {

        private final WeakHashMap<Elements, Elements> evalResults = new WeakHashMap<Elements, Elements>();

        private final Method method;
        private final Object[] args;

        public MethodInvocationImpl(Finder<?> finder, InternalFinder parent, Method method, Object[] args) {
            super(finder, parent);
            this.method = method;
            this.args = args;
        }

        @Override
        public Elements eval(Elements root) {
            if (evalResults.containsKey(root)) {
                return evalResults.get(root);
            }
            Elements elems = root;
            if (parent != null) {
                elems = parent.eval(root);
            }
            try {
                Elements result = (Elements) method.invoke(elems, evalArgs(root, args));
                evalResults.put(root, result);
                return result;
            } catch (InvocationTargetException e) {
                throw Throwables.propagate(e.getTargetException());
            } catch (IllegalAccessException | IllegalArgumentException e) {
                throw Throwables.propagate(e);
            }
        }

        private Object[] evalArgs(Elements root, Object[] args) {
            if (args == null) return null;
            Object[] evalArgs = new Object[args.length];

            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof InternalFinder) {
                    arg = ((InternalFinder) arg).eval(root);
                }
                evalArgs[i] = arg;
            }
            return evalArgs;
        }
    }
}
