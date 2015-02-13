package minium.internal;

import static java.lang.String.format;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

import minium.Elements;
import minium.FindElements;
import minium.Locator;
import platypus.AbstractMixinInitializer;
import platypus.InstanceProviders;
import platypus.Mixin;
import platypus.MixinClass;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

public interface InternalLocator extends Elements {

    public static final Method FIND_METHOD = Reflections.getDeclaredMethod(FindElements.class, "find", String.class);

    public Elements eval(Elements elems);

    public boolean isRoot();

    public abstract Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

    class Impl extends Mixin.Impl implements InternalLocator, InvocationHandler {

        protected final Locator<?> locator;
        protected final InternalLocator parent;

        public Impl(Locator<?> locator, InternalLocator parent) {
            this.locator = locator;
            this.parent = parent;
        }

        @Override
        public Elements eval(Elements elems) {
            return parent == null ? elems : parent.eval(elems);
        }

        @Override
        public boolean isRoot() {
            return parent == null;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<?> returnIntf = method.getReturnType();
            if (!Elements.class.isAssignableFrom(returnIntf)) {
                // let's check if Minium has some root set
                Elements root = locator.getRoot();
                Preconditions.checkState(root != null, "Method %s does not return an Elements class and Minium.get() does not return any root Elements", method);

                Elements elements = eval(root);
                return method.invoke(elements, args);
            }
            return createInternalLocator(locator, this, method, args);
        }

        @Override
        public String toString() {
            return format("%s.root()", locator);
        }

        public static <T> T createInternalLocator(final Locator<?> locator, InternalLocator parent) {
            final InternalLocator.Impl internalLocator = new InternalLocator.Impl(locator, parent);
            return doCreateInternalLocator(locator, internalLocator);
        }

        public static <T> T createInternalLocator(final Locator<?> locator, InternalLocator parent, Method method, Object ... args) {
            final InternalLocator.MethodInvocationImpl internalLocator = new InternalLocator.MethodInvocationImpl(locator, parent, method, args);
            return doCreateInternalLocator(locator, internalLocator);
        }

        private static <T> T doCreateInternalLocator(final Locator<?> locator, final InternalLocator.Impl internalLocator) {
            @SuppressWarnings("unchecked")
            final MixinClass<T> mixinClass = (MixinClass<T>) locator.getMixinClass();
            return mixinClass.newInstance(new AbstractMixinInitializer() {
                @Override
                protected void initialize() {
                    implement(Object.class, InternalLocator.class).with(InstanceProviders.ofInstance(internalLocator));
                    implementRemainers().with(InstanceProviders.adapt(internalLocator, mixinClass.getDeclaredInterfaces()));
                }
            });
        }
    }

    static class MethodInvocationImpl extends Impl implements InternalLocator {

        private final WeakHashMap<Elements, Elements> evalResults = new WeakHashMap<Elements, Elements>();

        private final Method method;
        private final Object[] args;

        public MethodInvocationImpl(Locator<?> locator, InternalLocator parent, Method method, Object[] args) {
            super(locator, parent);
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

        @Override
        public String toString() {
            Elements root = locator.getRoot();
            if (root == null) return super.toString();
            Elements elements = eval(root);
            return elements.toString();
        }

        private Object[] evalArgs(Elements root, Object[] args) {
            if (args == null) return null;
            Object[] evalArgs = new Object[args.length];

            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof InternalLocator) {
                    arg = ((InternalLocator) arg).eval(root);
                }
                evalArgs[i] = arg;
            }
            return evalArgs;
        }
    }
}
