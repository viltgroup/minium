package minium.internal;

import minium.AsIs;
import platypus.Mixin;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

public class Chainable<T> extends Mixin.Impl implements AsIs {

    @SuppressWarnings("serial")
    private final TypeToken<T> typeVariableToken = new TypeToken<T>(getClass()) {};

    protected TypeToken<T> typeVariableToken() {
        return typeVariableToken;
    }

    @SuppressWarnings("unchecked")
    protected <C> TypeToken<C> typeTokenFor(Class<?> clazz) {
        return (TypeToken<C>) TypeToken.of(clazz).where(new TypeParameter<T>() {}, typeVariableToken);
    }

    protected T myself() {
        return this.as(typeVariableToken);
    }
}
