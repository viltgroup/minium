package minium.web.internal.expression;

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;
import minium.web.internal.WebElementFunctions;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

public class NativeWebElementsExpression extends BaseExpression {

    private final List<? extends WebElement> nativeWebElements;

    public NativeWebElementsExpression(WebElement ... nativeWebElements) {
        this(Lists.newArrayList(nativeWebElements));
    }

    public NativeWebElementsExpression(List<? extends WebElement> nativeWebElements) {
        this.nativeWebElements = nativeWebElements;
    }

    @Override
    public String getJavascript(VariableGenerator varGenerator) {
        return format("$(%s)", varGenerator.generate());
    }

    @Override
    public Object[] getArgs() {
        List<WebElement> unwrappedNativedWebElements = from(nativeWebElements).transform(WebElementFunctions.unwrap()).toList();
        return new Object[] { unwrappedNativedWebElements };
    }

}
