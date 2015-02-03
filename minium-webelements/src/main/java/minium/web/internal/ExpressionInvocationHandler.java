package minium.web.internal;

import java.lang.reflect.Method;
import java.util.Set;

import minium.BasicElements;
import minium.Elements;
import minium.ElementsFactory;
import minium.internal.InternalElementsFactory;
import minium.internal.Reflections;
import minium.web.DocumentWebDriver;
import minium.web.MultipleDocumentDriversFoundException;
import minium.web.NoDocumentDriverFoundException;
import minium.web.WebElements;
import minium.web.internal.drivers.JavascriptInvoker;
import minium.web.internal.expression.Coercer;
import minium.web.internal.expression.Expression;
import minium.web.internal.expression.FunctionInvocationExpression;
import minium.web.internal.expression.VariableGenerator;

import com.google.common.base.Defaults;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.TypeToken;

public class ExpressionInvocationHandler<T extends WebElements> extends AbstractInvocationHandler {

    private static final Method SIZE_METHOD = Reflections.getDeclaredMethod(BasicElements.class, "size");

    private static class DocumentDriverInvoker implements Function<DocumentWebDriver, Object> {

        private final JavascriptInvoker javascriptInvoker;
        private final Expression expression;

        private boolean initialized;
        private String expressionJavascript;
        private Object[] expressionArgs;

        public DocumentDriverInvoker(JavascriptInvoker javascriptInvoker, Expression expression) {
            this.javascriptInvoker = javascriptInvoker;
            this.expression = expression;
        }

        @Override
        public Object apply(DocumentWebDriver documentDriver) {
            if (!initialized) {
                VariableGenerator varGenerator = new VariableGenerator.Impl();
                expressionJavascript = expression.getJavascript(varGenerator);
                expressionArgs = expression.getArgs();
                initialized = true;
            }
            return javascriptInvoker.invokeExpression(documentDriver, expressionJavascript, expressionArgs);
        }
    }

    @SuppressWarnings("serial")
    private final TypeToken<T> typeVariableToken = new TypeToken<T>(getClass()) {};

    private final ElementsFactory<?> factory;
    private final Coercer coercer;

    public ExpressionInvocationHandler(ElementsFactory<?> factory, Coercer coercer) {
        this.factory = factory;
        this.coercer = coercer;
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        Preconditions.checkArgument(proxy instanceof WebElements);
        T parent = ((WebElements) proxy).as(typeVariableToken);
        ExpressionWebElements webElements = factory.as(InternalElementsFactory.class).createMixin(parent, new DefaultExpressionWebElements<T>(method.getName(), args)).as(ExpressionWebElements.class);

        Class<?> returnClazz = method.getReturnType();

        if (returnClazz != Object.class && Elements.class.isAssignableFrom(returnClazz)) {
            return webElements.as(typeVariableToken);
        }

        JavascriptInvoker javascriptInvoker = parent.as(HasJavascriptInvoker.class).javascriptInvoker();
        Object result;

        if (returnClazz == Void.TYPE) {
            Iterable<DocumentWebDriver> documentDrivers = parent.as(InternalWebElements.class).documentDrivers();
            DocumentDriverInvoker documentDriverInvoker = new DocumentDriverInvoker(javascriptInvoker, webElements.getExpression());
            for (DocumentWebDriver documentDriver : documentDrivers) {
                documentDriverInvoker.apply(documentDriver);
            }
            result = null;
        } else {
            // materialize document drivers
            Set<DocumentWebDriver> documentDrivers = Sets.newLinkedHashSet(parent.as(InternalWebElements.class).documentDrivers());

            switch (documentDrivers.size()) {
            case 0:
                // special case for size, if no document was found then size is 0 for sure
                if (method.equals(SIZE_METHOD)) {
                    return 0;
                }
                throw new NoDocumentDriverFoundException("The expression has no frame or window to be evaluated to");
            case 1:
                result = getSingleDocumentDriverResult(Iterables.get(documentDrivers, 0), javascriptInvoker, webElements);
                break;
            default:
                Expression parentExpression = parent.as(ExpressionWebElements.class).getExpression();
                Expression sizeExpression = new FunctionInvocationExpression(parentExpression, "size");

                DocumentWebDriver webDriverWithResults = getCandidateDocumentDriver(javascriptInvoker, sizeExpression, documentDrivers);

                if (webDriverWithResults != null) {
                    DocumentDriverInvoker documentDriverInvoker = new DocumentDriverInvoker(javascriptInvoker, webElements.getExpression());
                    result = documentDriverInvoker.apply(webDriverWithResults);
                } else {
                    result = Defaults.defaultValue(returnClazz);
                }
                break;
            }
        }

        return coercer.coerce(result, method.getGenericReturnType());
    }

    protected DocumentWebDriver getCandidateDocumentDriver(JavascriptInvoker javascriptInvoker, Expression sizeExpression,
            Iterable<DocumentWebDriver> documentDrivers) {
        DocumentWebDriver webDriverWithResults = null;

        boolean multipleCandidates = false;
        for (DocumentWebDriver candidate : documentDrivers) {
            DocumentDriverInvoker documentDriverInvoker = new DocumentDriverInvoker(javascriptInvoker, sizeExpression);
            long size = (Long) documentDriverInvoker.apply(candidate);
            if (size > 0) {
                if (webDriverWithResults == null) {
                    webDriverWithResults = candidate;
                } else {
                    multipleCandidates = true;
                }
            }
        }

        if (multipleCandidates) {
            throw new MultipleDocumentDriversFoundException("Several frames or windows match the same expression, so value cannot be computed");
        }
        return webDriverWithResults;
    }


    protected Object getSingleDocumentDriverResult(DocumentWebDriver documentDriver, JavascriptInvoker javascriptInvoker, ExpressionWebElements webElements) {
        DocumentDriverInvoker documentDriverInvoker = new DocumentDriverInvoker(javascriptInvoker, webElements.getExpression());
        return documentDriverInvoker.apply(documentDriver);
    }
}
