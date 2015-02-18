package minium.script.dynjs;

import minium.web.internal.WebModule;
import minium.web.internal.WebElementsFactory.Builder;
import minium.web.internal.expression.BasicExpression;
import minium.web.internal.expression.Expression;
import minium.web.internal.expression.Expressionizer;

import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.JSFunction;

public class DynJsWebModules {

    public static class FunctionExpressionizer implements Expressionizer {

        private ExecutionContext executionContext;

        public FunctionExpressionizer(ExecutionContext executionContext) {
            this.executionContext = executionContext;
        }

        @Override
        public boolean handles(Object obj) {
            return obj instanceof JSFunction;
        }

        @Override
        public Expression apply(Object obj) {
            JSFunction fn = (JSFunction) obj;
            JSFunction toString = (JSFunction) fn.get(executionContext, "toString");
            return new BasicExpression((String) executionContext.call(toString, fn));
        }
    }

    public static WebModule dynJsModule(final ExecutionContext executionContext) {
        return new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                builder
                .withJsResources(
                        "minium/web/internal/lib/jquery.min.js",
                        "minium/script/js/internal/lib/jquery.functionCall.js"
                )
                .implementingInterfaces(JsFunctionWebElements.class)
                .withExpressionizers(new FunctionExpressionizer(executionContext));
            }
        };
    }
}
