package minium.internal;

import io.platypus.Mixin;
import minium.Elements;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

public abstract class BaseElements<T extends Elements> extends Mixin.Impl implements Elements {

    @SuppressWarnings("serial")
    private final TypeToken<T> typeVariableToken = new TypeToken<T>(getClass()) {};

    protected T myself() {
        return this.as(typeVariableToken);
    }

    protected TypeToken<T> typeVariableToken() {
        return typeVariableToken;
    }

    @SuppressWarnings("unchecked")
    protected <C> TypeToken<C> typeTokenFor(Class<?> clazz) {
        return (TypeToken<C>) TypeToken.of(clazz).where(new TypeParameter<T>() {}, typeVariableToken);
    }

    protected InternalElementsFactory<T> internalFactory() {
        TypeToken<InternalElementsFactory<T>> factoryTypeToken = typeTokenFor(InternalElementsFactory.class);
        return this.as(HasElementsFactory.class).factory().as(factoryTypeToken);
    }

    protected Elements parent() {
        return this.is(HasParent.class) ? this.as(HasParent.class).parent() : null;
    }
}