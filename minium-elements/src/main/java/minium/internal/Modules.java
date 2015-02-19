package minium.internal;

import static java.lang.String.format;
import minium.internal.ElementsFactory.Builder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

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

            @Override
            public String toString() {
                return format("Module%s", Iterables.toString(modules));
            }
        };
    }

    public static Module<?> combine(final Module<?> ... modules) {
        return combine(ImmutableList.copyOf(modules));
    }
}
