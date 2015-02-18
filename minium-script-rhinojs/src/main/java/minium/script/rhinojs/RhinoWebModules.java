package minium.script.rhinojs;

import minium.web.internal.WebElementsFactory.Builder;
import minium.web.internal.WebModule;
import minium.web.internal.expression.BasicExpression;
import minium.web.internal.expression.Expression;
import minium.web.internal.expression.Expressionizer;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;

public class RhinoWebModules {

    public static class FunctionExpressionizer implements Expressionizer {

        @Override
        public boolean handles(Object obj) {
            return obj instanceof Function;
        }

        @Override
        public Expression apply(Object obj) {
            Function fn = (Function) obj;
            Function toString = (Function) fn.getPrototype().get("toString", fn);
            Context cx = Context.enter();
            return new BasicExpression((String) toString.call(cx, fn, fn, new Object[0]));
        }
    }

    public static WebModule rhinoModule() {
        return new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                builder
                .withJsResources(
                        "minium/web/internal/lib/jquery.min.js",
                        "minium/script/js/internal/lib/jquery.functionCall.js"
                )
                .implementingInterfaces(JsFunctionWebElements.class)
                .withExpressionizers(new FunctionExpressionizer());
            }

            @Override
            public String toString() {
                return "WebModule[rhino]";
            }
        };
    }
}
