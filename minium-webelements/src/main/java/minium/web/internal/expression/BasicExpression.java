package minium.web.internal.expression;

public class BasicExpression extends BaseExpression {

    private final String javascript;

    public BasicExpression(String javascript) {
        this.javascript = javascript;
    }

    @Override
    public String getJavascript(VariableGenerator varGenerator) {
        return javascript;
    }

}
