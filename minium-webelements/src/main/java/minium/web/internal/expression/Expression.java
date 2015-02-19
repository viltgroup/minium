package minium.web.internal.expression;

public interface Expression {

    public String getJavascript(VariableGenerator varGenerator);

    /**
     *
     * @return argument if any
     */
    public Object[] getArgs();

}
