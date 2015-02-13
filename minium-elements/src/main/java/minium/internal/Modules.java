package minium.internal;

import minium.internal.ElementsFactory.Builder;

import com.google.common.collect.ImmutableList;

public class Modules {

    public static Module<?> combine(final Iterable<? extends Module<?>> modules) {
        return new Module<Builder<?>>() {
            @Override
            @SuppressWarnings({ "unchecked", "rawtypes" })
            public void configure(Builder<?> builder) {
                for (Module module : modules) {
                    module.configure(builder);
                }
            }
        };
    }

    public static Module<?> combine(final Module<?> ... modules) {
        return combine(ImmutableList.copyOf(modules));
    }
}
