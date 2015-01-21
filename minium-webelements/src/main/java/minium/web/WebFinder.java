package minium.web;

import io.platypus.AbstractMixinInitializer;
import io.platypus.MixinClass;
import io.platypus.MixinClasses;

import java.lang.reflect.Method;

import minium.Elements;

import com.google.common.base.Preconditions;
import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.TypeToken;

public class WebFinder<T extends Elements> {

    private static final class ElementsProxy implements Elements {

        @Override
        public boolean is(Class<?> clazz) {
            return Elements.class.isAssignableFrom(clazz);
        }

        @Override
        public boolean is(TypeToken<?> type) {
            return Elements.class.isAssignableFrom(type.getRawType());
        }

        @Override
        public <T> T as(Class<T> clazz) {
            return createElementsProxy(clazz);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T as(TypeToken<T> type) {
            return (T) createElementsProxy(type.getRawType());
        }
    }

    private static final class ProxyInvocationHandler extends AbstractInvocationHandler {

        private TypeToken<?> typeToken;

        public ProxyInvocationHandler(Class<?> clazz) {
            this.typeToken = TypeToken.of(clazz);
        }

        @Override
        protected Object handleInvocation(Object thisObj, Method method, Object[] args) throws Throwable {
            Class<?> type = typeToken.resolveType(method.getGenericReturnType()).getRawType();
            Preconditions.checkState(type instanceof Class, "Method %s does not return a class type", method);
            final Class<?> returnClazz = type;
            Preconditions.checkState(Elements.class.isAssignableFrom(returnClazz), "Method %s does not return an Elements class", method);
            return createElementsProxy(returnClazz);
        }
    }

    public static <WE extends WebElements> WebFinder<WE> by(Class<WE> clazz) {
        return new WebFinder<WE>(clazz);
    }

    private Class<T> clazz;

    public WebFinder(Class<T> clazz) {
        Preconditions.checkArgument(clazz.isInterface(), "class %s is not an interface", clazz);
        this.clazz = clazz;
    }

    public T cssSelector(String selector) {
        return createElementsProxy(clazz);
    }

    public T name() {
        return null;
    }

    protected static <T> T createElementsProxy(final Class<T> clazz) {
        MixinClass<T> returnClassMixin = MixinClasses.create(clazz);
        return returnClassMixin.newInstance(new AbstractMixinInitializer() {
            @Override
            protected void initialize() {
                implement(Elements.class).with(new ElementsProxy());
                implement(clazz).with(new ProxyInvocationHandler(clazz));
            }
        });
    }
}
