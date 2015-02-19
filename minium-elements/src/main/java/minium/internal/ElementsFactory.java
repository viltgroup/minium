package minium.internal;

import java.util.Set;

import minium.AsIs;
import minium.Elements;

public interface ElementsFactory<T extends Elements> extends AsIs {

    public abstract T createRoot();

    public Set<Class<?>> getProvidedInterfaces();

    public interface Builder<EF extends ElementsFactory<?>> {
        public abstract EF build();
    }
}

