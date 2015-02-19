package minium.web.internal.expression;

public class RootExpression extends BaseExpression {

    @Override
    public String getJavascript(VariableGenerator varGenerator) {
        return "$(\":eq(0)\")";
    }
}
