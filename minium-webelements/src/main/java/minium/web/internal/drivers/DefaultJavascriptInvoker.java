/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.web.internal.drivers;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import minium.web.internal.utils.ResourceFunctions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * This class is responsible for injecting the necessary javascript code in the
 * current page so that jQuery expressions can be evaluated.
 *
 * @author Rui
 */
public class DefaultJavascriptInvoker implements JavascriptInvoker {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJavascriptInvoker.class);

    private static final String ARGS_DECLARATION = "var args = Array.prototype.slice.call(arguments)";

    enum ResponseType {
        MINIUM_UNDEFINED("minium-undefined"),
        NULL("null"),
        JSON("json"),
        ARRAY("array"),
        NUMBER("number"),
        STRING("string"),
        BOOLEAN("boolean"),
        EXCEPTION("exception");

        private String typeStr;

        private ResponseType(String typeStr) {
            this.typeStr = typeStr;
        }

        public static ResponseType of(String typeStr) {
            for (ResponseType type : ResponseType.values()) {
                if (Objects.equal(type.typeStr, typeStr)) return type;
            }
            throw new IllegalArgumentException(String.format("Type %s is not valid", typeStr));
        }

        @Override
        public String toString() {
            return typeStr;
        }
    }

    // resource paths
    private final Collection<String> jsResources;
    private final Collection<String> cssResources;

    // concatenated styles resources content
    private final String styles;

    private final ClassLoader classLoader;

    private final String setMiniumVarTemplate;
    private final String evalExpressionTemplate;

    public DefaultJavascriptInvoker(ClassLoader classLoader, Collection<String> jsResources, Collection<String> cssResources) {
        this.classLoader = classLoader;
        this.jsResources = jsResources;
        this.cssResources = cssResources;

        LOGGER.debug("DefaultJavascriptInvoker initialized with:");
        LOGGER.debug("  jsResources  : {}", jsResources);
        LOGGER.debug("  cssResources : {}", cssResources);

        String miniumJqueryScript = getJsContent("minium/web/internal/lib/minium-jquery.min.js");
        String jsScripts = combineResources(jsResources);
        styles = cssResources != null ? combineResources(cssResources) : null;

        setMiniumVarTemplate  = new StringBuilder()
            // exposes minium global variable
            .append(miniumJqueryScript).append(";")
            .append("(function (window, jQuery, $, styles) {")
            // loads jQuery extensions (note that both jQuery and $ local variables
            // correspond to minium.$
            .append(jsScripts).append(";")
            .append("minium.loadStyles(styles);")
            .append("})(window, minium.$, minium.$, args.shift());")
            .toString();
        evalExpressionTemplate = "return typeof minium==='undefined'?['minium-undefined']:minium.evalExpression(args.shift(),args);";
    }

    /* (non-Javadoc)
     * @see minium.web.internal.drivers.Foo#invoke(org.openqa.selenium.JavascriptExecutor, java.lang.String, java.lang.Object)
     */
    @Override
    public <T> T invoke(JavascriptExecutor wd, String expression, Object... args) {
        return this.<T> doInvoke(wd, expression, args);
    }

    @Override
    public <T> T invokeExpression(JavascriptExecutor executor, String expression, Object... args) {
        return this.<T>invoke(executor, expression, args);
    }

    @SuppressWarnings("unchecked")
    protected <T> T doInvoke(JavascriptExecutor wd, String expression, Object... args) {
        try {
            Object[] fullArgs = createLightInvokerScriptArgs(expression, args);

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("About to invoke light invoker: {}", fullArgs);
            }

            Object result = wd.executeScript(lightInvokerScript(), fullArgs);

            LOGGER.trace("result: {}", result);

            List<?> response = getValidResponse(result);
            ResponseType type = ResponseType.of((String) response.get(0));

            if (type == ResponseType.MINIUM_UNDEFINED) {
                // minium is not defined yet, we need to send all the necessary javascript
                fullArgs = createFullInvokerScriptArgs(expression, args);

                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("About to invoke full invoker: {}", fullArgs);
                }

                result = wd.executeScript(fullInvokerScript(), fullArgs);

                LOGGER.trace("result: {}", result);

                response = getValidResponse(result);
                type = ResponseType.of((String) response.get(0));
            }

            return (T) extractValue(type, response);

        } catch (WebDriverException e) {
            throw new JavascriptInvocationFailedException(format("Failed invoking expression:\n", expression), e);
        }
    }

    private Object extractValue(ResponseType type, List<?> response) {
        switch (type) {
        case MINIUM_UNDEFINED:
            throw new IllegalStateException("Should not be here...");
        case NULL:
            return null;
        case ARRAY:
            return response.subList(1, response.size());
//        case EXCEPTION:
//            Object object = response.get(1);
//            throw new JavascriptInvocationFailedException(format("Failed invoking expression:\n", expression), e);
        default:
            return response.get(1);
        }
    }

    protected List<?> getValidResponse(Object result) {
        Preconditions.checkState(result instanceof List || result instanceof WebElement, "Expected a list or webelement as response but got %s", result);

        List<?> response;

        if (result instanceof WebElement) {
            response = ImmutableList.of("array", result);
        } else {
            response = (List<?>) result;
            Preconditions.checkState(!response.isEmpty(), "Expected response list not to be empty");
            Object first = response.get(0);
            Preconditions.checkState(first instanceof String || first instanceof WebElement, "Expected a string or webelement value in the first position of the list but got %s", result);
        }

        return response;
    }

    protected Object[] createLightInvokerScriptArgs(String expr, Object... args) {
        List<Object> fullArgs = Lists.<Object>newArrayList(expr);
        if (args != null && args.length > 0) fullArgs.addAll(Arrays.asList(args));
        return fullArgs.toArray(new Object[fullArgs.size()]);
    }

    protected Object[] createFullInvokerScriptArgs(String expr, Object... args) {
        List<Object> fullArgs = Lists.<Object>newArrayList(styles, expr);
        if (args != null && args.length > 0) fullArgs.addAll(Arrays.asList(args));
        return fullArgs.toArray(new Object[fullArgs.size()]);
    }

    protected Object[] createFullInvokerScriptArgsAfterPartitionedLoad(String expr, Object... args) {
        List<Object> fullArgs = Lists.<Object>newArrayList("", "", expr);
        if (args != null && args.length > 0) fullArgs.addAll(Arrays.asList(args));
        return fullArgs.toArray(new Object[fullArgs.size()]);
    }

    protected String lightInvokerScript() {
        return Joiner.on("; ").join(ARGS_DECLARATION, evalExpressionTemplate);
    }

    protected String fullInvokerScript() {
        return Joiner.on("; ").join(ARGS_DECLARATION, setMiniumVarTemplate, evalExpressionTemplate);
    }

    protected Collection<String> getJsResources() {
        return jsResources;
    }

    protected Collection<String> getCssResources() {
        return cssResources;
    }

    protected String getJsContent(String filePath) {
        return ResourceFunctions.classpathFileToStringFunction(classLoader).apply(filePath);
    }

    protected String combineResources(Collection<String> resources) {
        return Joiner.on("\n\n").join(Collections2.transform(resources, ResourceFunctions.classpathFileToStringFunction(classLoader)));
    }

}
