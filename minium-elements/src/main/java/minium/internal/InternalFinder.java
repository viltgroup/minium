package minium.internal;

import io.platypus.AbstractMixinInitializer;
import io.platypus.InstanceProviders;
import io.platypus.Mixin;
import io.platypus.MixinClass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

import minium.Elements;
import minium.Minium;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

/**
 * Elements classes used in finder chain calls MUST NOT use reified generics. The only exception is
 * {@link Elements#as(Class)}.
 *
 * For instance, the following code is not supported:
 *
 * <pre><code>
 * public interface TestElements implements Elements {
 *   <T extends Elements> T test();
 * }
 *
 * // this will throw an Exception
 * TestElements testElems = Finder.by(TestElements.class).selector(":text").test();
 * </code></pre>
 *
 * Basically, those restriction is due to type erasure, and Mockito suffers from the same problem
 * (http://stackoverflow.com/questions/15906220/classcastexception-with-generics-and-mockito).
 *
 * @author rui.figueira
 */
public interface InternalFinder {

    public Elements eval(Elements elems);

    public abstract Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

    static class Impl extends Mixin.Impl implements InternalFinder, InvocationHandler {

        protected final MixinClass<?> mixinClass;
        protected final InternalFinder parent;

        public Impl(io.platypus.MixinClass<?> mixinClass, InternalFinder parent) {
            this.mixinClass = mixinClass;
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
                Elements root = Minium.get();
                Preconditions.checkState(root != null, "Method %s does not return an Elements class and Minium.get() does not return any root Elements", method);

                Elements elements = eval(root);
                return method.invoke(elements, args);
            }
            return createInternalFinder(mixinClass, this, method, args);
        }

        public static <T> T createInternalFinder(final MixinClass<T> mixinClass, InternalFinder parent) {
            final InternalFinder.Impl internalFinder = new InternalFinder.Impl(mixinClass, parent);
            return doCreateInternalFinder(mixinClass, internalFinder);
        }

        public static <T> T createInternalFinder(final MixinClass<T> mixinClass, InternalFinder parent, Method method, Object ... args) {
            final InternalFinder.MethodInvocationImpl internalFinder = new InternalFinder.MethodInvocationImpl(mixinClass, parent, method, args);
            return doCreateInternalFinder(mixinClass, internalFinder);
        }

        private static <T> T doCreateInternalFinder(final MixinClass<T> mixinClass, final InternalFinder.Impl internalFinder) {
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

        public MethodInvocationImpl(MixinClass<?> mixinClass, InternalFinder parent, Method method, Object[] args) {
            super(mixinClass, parent);
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
