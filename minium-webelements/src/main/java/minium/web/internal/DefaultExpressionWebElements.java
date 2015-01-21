package minium.web.internal;

import static com.google.common.collect.FluentIterable.from;
import minium.web.internal.drivers.DocumentWebElement;
import minium.web.internal.expression.Expression;
import minium.web.internal.expression.Expressionizer;
import minium.web.internal.expression.FunctionInvocationExpression;
import minium.web.internal.expression.NativeWebElementsExpression;
import minium.web.internal.expression.VariableGenerator;
import minium.web.DocumentRoots;
import minium.web.DocumentWebDriver;
import minium.web.WebElements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

public class DefaultExpressionWebElements<T extends WebElements> extends InternalWebElements.Impl<T> implements ExpressionWebElements {

    static class ExpressionFrozenWebElements<T extends WebElements> extends InternalWebElements.Impl<T> implements ExpressionWebElements {

        private List<DocumentWebElement> nativeWebElements;

        @Override
        public Iterable<DocumentWebDriver> candidateDocumentDrivers() {
            if (nativeWebElements == null) {
                return parent().as(InternalWebElements.class).candidateDocumentDrivers();
            } else {
                return from(wrappedNativeElements()).transform(WebElementFunctions.unwrapDocumentDriver());
            }
        }

        @Override
        public Iterable<DocumentWebDriver> documentDrivers() {
            if (nativeWebElements == null) {
                return parent().as(InternalWebElements.class).documentDrivers();
            } else {
                return from(wrappedNativeElements()).transform(WebElementFunctions.unwrapDocumentDriver());
            }
        }

        @Override
        public DocumentRoots documentRoots() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterable<DocumentWebElement> wrappedNativeElements() {
            if (nativeWebElements == null) {
                List<DocumentWebElement> elems = ImmutableList.copyOf(parent().as(InternalWebElements.class).wrappedNativeElements());
                if (elems.isEmpty()) return Collections.emptyList();
                nativeWebElements = elems;
            }
            return nativeWebElements;
        }

        @Override
        public Expression getExpression() {
            List<DocumentWebElement> nativeElems = ImmutableList.copyOf(wrappedNativeElements());
            return new NativeWebElementsExpression(nativeElems);
        }

        @Override
        public T freeze() {
            return myself();
        }

        @Override
        public String toString() {
            return parent().toString() + ".freeze()";
        }
    }

    private final String function;
    private final Object[] args;

    public DefaultExpressionWebElements(String function, Object... args) {
        this.function = function;
        this.args = args == null ? new Object[0] : args;
    }

    @Override
    public Expression getExpression() {
        Expression parentExpression = parent().as(ExpressionWebElements.class).getExpression();
        Expressionizer expressionizer = this.as(HasExpressionizer.class).getExpressionizer();

        List<Expression> argExpressions = from(Arrays.asList(args)).transform(expressionizer).toList();

        return new FunctionInvocationExpression(parentExpression, function, argExpressions);
    }

    @Override
    public Iterable<DocumentWebDriver> candidateDocumentDrivers() {
        return parent().as(InternalWebElements.class).candidateDocumentDrivers();
    }

    @Override
    public DocumentRoots documentRoots() {
        return parent().as(InternalWebElements.class).documentRoots();
    }

    @Override
    public Iterable<DocumentWebDriver> documentDrivers() {
        return from(wrappedNativeElements()).transform(WebElementFunctions.unwrapDocumentDriver());
    }

    @Override
    public T freeze() {
        return internalFactory().createMixin(myself(), new ExpressionFrozenWebElements<T>());
    }

    @Override
    public String toString() {
        String rootString = documentRoots().toString();
        String expression = getExpression().getJavascript(new VariableGenerator.Impl());
        return Strings.isNullOrEmpty(rootString) ? expression : String.format("%s -> %s", rootString, expression);
    }

}
