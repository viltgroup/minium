package minium.web.internal.expression;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public interface Expressionizer extends Function<Object, Expression> {

    boolean handles(Object obj);

    @Override
    public Expression apply(Object obj);

    public static class Composite implements Expressionizer {

        private final List<Expressionizer> expressionizers = Lists.newArrayList();

        public Composite add(Expressionizer processor) {
            expressionizers.add(processor);
            return this;
        }

        public Composite addAll(Collection<? extends Expressionizer> processors) {
            expressionizers.addAll(processors);
            return this;
        }

        @Override
        public boolean handles(Object obj) {
            Expressionizer processor = expressionizerFor(obj);
            return processor != null;
        }

        @Override
        public Expression apply(Object obj) {
            Expressionizer processor = expressionizerFor(obj);
            return processor.apply(obj);
        }

        protected Expressionizer expressionizerFor(Object obj) {
            for (Expressionizer expressionizer : Lists.reverse(expressionizers)) {
                if (expressionizer.handles(obj)) return expressionizer;
            }
            return null;
        }
    }

}