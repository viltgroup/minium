package minium.web.internal;

import minium.web.internal.expression.Expressionizer;

public interface HasExpressionizer {

    public Expressionizer getExpressionizer();

    public static class Impl implements HasExpressionizer {

        private final Expressionizer expressionizer;

        public Impl(Expressionizer expressionizer) {
            this.expressionizer = expressionizer;
        }

        @Override
        public Expressionizer getExpressionizer() {
            return expressionizer;
        }

    }
}
