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

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import minium.web.internal.ResourceException;
import minium.web.internal.compressor.Compressor;
import minium.web.internal.compressor.ResourceFunctions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;

/**
 * This class is responsible for injecting the necessary javascript code in the
 * current page so that jQuery expressions can be evaluated.
 *
 * @author Rui
 */
public class DefaultJavascriptInvoker implements JavascriptInvoker {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJavascriptInvoker.class);

    private static final String CLOSURE_COMPRESSOR_CLASSNAME = "minium.web.internal.compressor.ClosureCompressor";
    private static final String CLOSURE_COMPILER_CLASSNAME = "com.google.javascript.jscomp.Compiler";

    private static final String INTERNAL_TEMPLATES_PATH = "minium/web/internal/templates/";

    private static final String FULL_TEMPLATE_PATH = INTERNAL_TEMPLATES_PATH + "jquery-invoker-full.template";
    private static final String LIGHT_TEMPLATE_PATH = INTERNAL_TEMPLATES_PATH + "jquery-invoker-light.template";

    private static Compressor compressor;

    static {
        try {
            Class.forName(CLOSURE_COMPILER_CLASSNAME, false, DefaultJavascriptInvoker.class.getClassLoader());
            compressor = (Compressor) Class.forName(CLOSURE_COMPRESSOR_CLASSNAME).newInstance();
        } catch (Exception e) {
            LOGGER.info("Google Closure Compiler not found in classpath, javascript will not be compressed");
            compressor = Compressor.NULL;
        }
    }

    enum ResponseType {
        MINIUM_UNDEFINED("minium-undefined"),
        NULL("null"),
        JSON("json"),
        ARRAY("array"),
        NUMBER("number"),
        STRING("string"),
        BOOLEAN("boolean");

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
    private final String lightInvokerScriptTemplate;
    private final String fullInvokerScriptTemplate;

    public DefaultJavascriptInvoker(ClassLoader classLoader, Collection<String> jsResources, Collection<String> cssResources) {
        this.classLoader = classLoader;
        this.jsResources = jsResources;
        this.cssResources = cssResources;

        try {
            LOGGER.debug("DefaultJavascriptInvoker initialized with:");
            LOGGER.debug("  jsResources  : {}", jsResources);
            LOGGER.debug("  cssResources : {}", cssResources);

            String jsScripts = compressor.compress(DefaultJavascriptInvoker.class.getClassLoader(), jsResources);

            styles = cssResources != null ? combineResources(cssResources) : null;

            lightInvokerScriptTemplate = getFileContent(LIGHT_TEMPLATE_PATH);
            fullInvokerScriptTemplate  = getFileContent(FULL_TEMPLATE_PATH).replace("{{jsScript}}", jsScripts);
        } catch (IOException e) {
            throw new ResourceException(e);
        }
    }

    /* (non-Javadoc)
     * @see minium.web.internal.drivers.Foo#invoke(org.openqa.selenium.JavascriptExecutor, java.lang.String, java.lang.Object)
     */
    @Override
    public <T> T invoke(JavascriptExecutor wd, String expression, Object... args) {
        return this.<T> doInvoke(wd, false, expression, args);
    }

    /* (non-Javadoc)
     * @see minium.web.internal.drivers.Foo#invokeAsync(org.openqa.selenium.JavascriptExecutor, java.lang.String, java.lang.Object)
     */
    @Override
    public <T> T invokeAsync(JavascriptExecutor wd, String expression, Object... args) {
        return this.<T> doInvoke(wd, true, expression, args);
    }

    @Override
    public <T> T invokeExpression(JavascriptExecutor executor, String expression, Object... args) {
        return this.<T>invoke(executor, format("return %s;", expression), args);
    }

    @Override
    public <T> T invokeExpressionAsync(JavascriptExecutor executor, String expression, Object... args) {
        return this.<T>invokeAsync(executor, format("return %s;", expression), args);
    }

    @SuppressWarnings("unchecked")
    protected <T> T doInvoke(JavascriptExecutor wd, boolean async, String expression, Object... args) {
        try {
            Object[] fullArgs = createLightInvokerScriptArgs(async, args);

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("About to invoke light invoker:");
                LOGGER.trace("async: {}", async);
                LOGGER.trace("expression: {}", expression);
                LOGGER.trace("fullArgs: {}", fullArgs);
            }

            Object result = async ?
                    wd.executeAsyncScript(lightInvokerScript(expression), fullArgs) :
                    wd.executeScript(lightInvokerScript(expression), fullArgs);

            LOGGER.trace("result: {}", result);

            List<?> response = getValidResponse(result);
            ResponseType type = ResponseType.of((String) response.get(0));

            if (type == ResponseType.MINIUM_UNDEFINED) {
                // minium is not defined yet, we need to send all the necessary javascript
                fullArgs = createFullInvokerScriptArgs(async, args);

                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("About to invoke full invoker:");
                    LOGGER.trace("fullArgs: {}", fullArgs);
                }

                result = async ?
                        wd.executeAsyncScript(fullInvokerScript(expression), fullArgs) :
                        wd.executeScript(fullInvokerScript(expression), fullArgs);

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
        default:
            return response.get(1);
        }
    }

    protected List<?> getValidResponse(Object result) {
        Preconditions.checkState(result instanceof List, "Expected a list as response");
        List<?> response = (List<?>) result;
        Preconditions.checkState(!response.isEmpty(), "Expected response list not to be empty");
        Object type = response.get(0);
        Preconditions.checkState(type instanceof String, "Expected a string value in the first position of the list");

        return response;
    }

    protected Object[] createLightInvokerScriptArgs(boolean async, Object... args) {
        Object[] fullArgs = args == null ? new Object[1] : new Object[args.length + 1];
        fullArgs[0] = async;
        if (args != null) System.arraycopy(args, 0, fullArgs, 1, args.length);
        return fullArgs;
    }

    protected Object[] createFullInvokerScriptArgs(boolean async, Object... args) {
        Object[] fullArgs;
        fullArgs = args == null ? new Object[2] : new Object[args.length + 2];
        fullArgs[0] = async;
        fullArgs[1] = styles;
        if (args != null) System.arraycopy(args, 0, fullArgs, 2, args.length);
        return fullArgs;
    }

    protected String lightInvokerScript(String expression) {
        return lightInvokerScriptTemplate.replace("{{expression}}", expression);
    }

    protected String fullInvokerScript(String expression) {
        return fullInvokerScriptTemplate.replace("{{expression}}", expression);
    }

    protected Collection<String> getJsResources() {
        return jsResources;
    }

    protected Collection<String> getCssResources() {
        return cssResources;
    }

    protected String getFileContent(String filePath) {
        return ResourceFunctions.classpathFileToStringFunction(classLoader).apply(filePath);
    }

    protected String combineResources(Collection<String> resources) {
        return Joiner.on("\n\n").join(Collections2.transform(resources, ResourceFunctions.classpathFileToStringFunction(classLoader)));
    }
}
