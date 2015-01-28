package minium.script.dynjs;

import minium.web.WebElementsFactory.Builder;
import minium.web.WebModule;
import minium.web.internal.expression.BasicExpression;
import minium.web.internal.expression.Expression;
import minium.web.internal.expression.Expressionizer;

import org.dynjs.runtime.JSFunction;

public class DynJsWebModules {

    public static class FunctionExpressionizer implements Expressionizer {

        @Override
        public boolean handles(Object obj) {
            return obj instanceof JSFunction;
        }

        @Override
        public Expression apply(Object obj) {
            return new BasicExpression(((JSFunction) obj).toString());
        }
    }

    public static WebModule dynJsModule() {
        return new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                builder
                .withJsResources(
                        "minium/web/internal/lib/jquery.min.js",
                        "minium/script/dynjs/internal/lib/jquery.functionCall.js"
                )
                .implementingInterfaces(FunctionCallWebElements.class)
                .withExpressionizers(new FunctionExpressionizer());
            }
        };
    }
}
