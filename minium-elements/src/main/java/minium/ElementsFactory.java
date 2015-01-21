package minium;

import io.platypus.AbstractMixinInitializer;
import io.platypus.InstanceProvider;
import io.platypus.Mixin;
import io.platypus.MixinClass;
import io.platypus.MixinClasses;
import io.platypus.internal.Casts;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

public interface ElementsFactory extends Mixin {

    public static class Builder<T extends ElementsFactory> {

        @SuppressWarnings("serial")
        private final TypeToken<T> typeVariableToken = new TypeToken<T>(getClass()) {};

        private final Map<Class<?>, InstanceProvider<?>> providers = Maps.newLinkedHashMap();

        public <O> Builder<T> implementWith(Class<O> factoryIntf, InstanceProvider<O> implProvider) {
            providers.put(factoryIntf, implProvider);
            return this;
        }

        public <O> Builder<T> implementWith(TypeToken<O> factoryTypeToken, InstanceProvider<O> implProvider) {
            providers.put(factoryTypeToken.getRawType(), implProvider);
            return this;
        }

        public T build() {
            MixinClass<T> elementsFactoryClass = Casts.unsafeCast(MixinClasses.create(typeVariableToken.getRawType(), providers.keySet()));
            return elementsFactoryClass.newInstance(new AbstractMixinInitializer() {
                @Override
                protected void initialize() {
                    for (Entry<Class<?>, InstanceProvider<?>> entry : providers.entrySet()) {
                        implement(entry.getKey()).with(entry.getValue());
                    }
                }
            });
        }
    }
}
