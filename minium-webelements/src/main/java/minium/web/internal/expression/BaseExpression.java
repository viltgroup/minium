package minium.web.internal.expression;


public abstract class BaseExpression implements Expression {

    @Override
    public Object[] getArgs() {
        return null;
    }

    @Override
    public String toString() {
        return getJavascript(new VariableGenerator.Impl());
    }

}
