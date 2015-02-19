package minium.web.internal;

import minium.web.internal.expression.Coercer;

public interface HasCoercer {

    public Coercer coercer();

    public static class Impl implements HasCoercer {

        private final Coercer coercer;

        public Impl(Coercer coercer) {
            this.coercer = coercer;
        }

        @Override
        public Coercer coercer() {
            return coercer;
        }

    }
}
