package minium.web.internal.drivers;

import org.openqa.selenium.JavascriptExecutor;

public interface JavascriptInvoker {

    public abstract <T> T invoke(JavascriptExecutor wd, String expression, Object ... args);

    public abstract <T> T invokeAsync(JavascriptExecutor wd, String expression, Object ... args);

    public abstract <T> T invokeExpression(JavascriptExecutor executor, String expression, Object ... args);

    public abstract <T> T invokeExpressionAsync(JavascriptExecutor executor, String expression, Object ... args);

}