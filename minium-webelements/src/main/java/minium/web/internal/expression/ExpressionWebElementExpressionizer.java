package minium.web.internal.expression;

import minium.Elements;
import minium.web.internal.ExpressionWebElements;

public class ExpressionWebElementExpressionizer implements Expressionizer {

    @Override
    public boolean handles(Object obj) {
        return obj instanceof Elements && ((Elements) obj).is(ExpressionWebElements.class);
    }

    @Override
    public Expression apply(Object obj) {
        ExpressionWebElements expressionWebElements = ((Elements) obj).as(ExpressionWebElements.class);
        return expressionWebElements.getExpression();
    }

}