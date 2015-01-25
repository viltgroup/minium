package minium.script.rhinojs;

import minium.web.WebElementsFactory.Builder;
import minium.web.WebModule;
import minium.web.internal.expression.BasicExpression;
import minium.web.internal.expression.Expression;
import minium.web.internal.expression.Expressionizer;

import org.mozilla.javascript.Function;

public class RhinoWebModules {

    public class FunctionExpressionizer implements Expressionizer {

        @Override
        public boolean handles(Object obj) {
            return obj instanceof Function;
        }

        @Override
        public Expression apply(Object obj) {
            return new BasicExpression(((Function) obj).toString());
        }
    }

    public static WebModule rhinoModule() {
        return new WebModule() {

            @Override
            public void configure(Builder<?> builder) {
                builder
                .withJsResources(
                        "minium/web/internal/lib/jquery.min.js",
                        "minium/script/rhinojs/internal/lib/jquery.functionCall.js"
                )
                .implementingInterfaces(FunctionCallWebElements.class);
            }
        };
    }
}
