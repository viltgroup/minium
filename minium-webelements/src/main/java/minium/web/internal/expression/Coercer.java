package minium.web.internal.expression;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;


public interface Coercer {

    boolean handles(Object obj, Type type);

    public Object coerce(Object obj, Type type);

    public static class Composite implements Coercer {

        private final List<Coercer> coercers = Lists.newArrayList();

        public Composite add(Coercer processor) {
            coercers.add(processor);
            return this;
        }

        public Composite addAll(Collection<? extends Coercer> processor) {
            coercers.addAll(processor);
            return this;
        }

        public Composite addAll(Coercer ... processor) {
            return addAll(Arrays.asList(processor));
        }

        @Override
        public boolean handles(Object obj, Type type) {
            Coercer coercer = coercerFor(obj, type);
            return coercer != null;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            Coercer coercer = Preconditions.checkNotNull(coercerFor(obj, type), "No coercer found for %s with type %s", obj, type);
            return coercer.coerce(obj, type);
        }

        protected Coercer coercerFor(Object obj, Type type) {
            for (Coercer coercer : Lists.reverse(coercers)) {
                if (coercer.handles(obj, type)) return coercer;
            }
            return null;
        }
    }

}