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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import minium.web.internal.ResourceException;
import minium.web.internal.compressor.Compressor;
import minium.web.internal.compressor.ResourceFunctions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

/**
 * This class is responsible for injecting the necessary javascript code in the
 * current page so that jQuery expressions can be evaluated.
 *
 * @author Rui
 */
public class DefaultJavascriptInvoker implements JavascriptInvoker {

    private static final int CHUNK_SIZE = 48 * 1024;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJavascriptInvoker.class);

    private static final String CLOSURE_COMPRESSOR_CLASSNAME = "minium.web.internal.compressor.ClosureCompressor";
    private static final String CLOSURE_COMPILER_CLASSNAME = "com.google.javascript.jscomp.Compiler";

    private static final String ARGS_DECLARATION = "var args = Array.prototype.slice.call(arguments)";

    private static final String INTERNAL_TEMPLATES_PATH = "minium/web/internal/templates/";

    private static final String LOAD_JS_STYLES_TEMPLATE_PATH = INTERNAL_TEMPLATES_PATH + "load-js-styles.template";
    private static final String SET_MINIUM_VAR_TEMPLATE_PATH = INTERNAL_TEMPLATES_PATH + "set-minium-var.template";
    private static final String EVAL_EXPR_TEMPLATE_PATH = INTERNAL_TEMPLATES_PATH + "eval-expression.template";

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

    // concatenated js resources content
    private String jsScripts;

    // concatenated styles resources content
    private final String styles;

    // md5 hash for jsScripts and styles
    private final String md5Hash;

    private final ClassLoader classLoader;
    private final String loadJsStylesTemplate;
    private final String setMiniumVarTemplate;
    private final String evalExpressionTemplate;

    public DefaultJavascriptInvoker(ClassLoader classLoader, Collection<String> jsResources, Collection<String> cssResources) {
        this.classLoader = classLoader;
        this.jsResources = jsResources;
        this.cssResources = cssResources;

        try {
            LOGGER.debug("DefaultJavascriptInvoker initialized with:");
            LOGGER.debug("  jsResources  : {}", jsResources);
            LOGGER.debug("  cssResources : {}", cssResources);

            jsScripts = compressor.compress(DefaultJavascriptInvoker.class.getClassLoader(), jsResources);
            styles = cssResources != null ? combineResources(cssResources) : null;

            // we compute both jsScripts and styles hash
            md5Hash = Hashing.md5().newHasher().putUnencodedChars(jsScripts).putUnencodedChars(styles == null ? "" : styles).hash().toString();

            loadJsStylesTemplate  = getJsContent(LOAD_JS_STYLES_TEMPLATE_PATH);
            setMiniumVarTemplate  = getJsContent(SET_MINIUM_VAR_TEMPLATE_PATH);
            evalExpressionTemplate = getJsContent(EVAL_EXPR_TEMPLATE_PATH);
        } catch (IOException e) {
            throw new ResourceException(e);
        }
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

                try {
                    result = wd.executeScript(fullInvokerScript(), fullArgs);
                } catch (WebDriverException e) {
                    String message = e.getMessage();
                    if (e instanceof UnreachableBrowserException || (message != null && message.startsWith("ERROR Selenium Server stopped responding"))) {
                        // this can happen when using RemoteWebDriver is Selendroid
                        // because it uses netty that by default limits HTTP message
                        // to 64KB (this is a bigger problem because selenium providers
                        // like Sauce Labs use the default Selendroid configuration)
                        // https://athena.vilt-group.com/issues/36744
                        LOGGER.debug("Failed sending the whole scripts at once, trying to send them in chunks");

                        loadPartitionedScriptsAndStyles(wd);
                        fullArgs = createFullInvokerScriptArgsAfterPartitionedLoad(expression, args);
                        result = wd.executeScript(fullInvokerScript(), fullArgs);
                    }
                }

                LOGGER.trace("result: {}", result);

                response = getValidResponse(result);
                type = ResponseType.of((String) response.get(0));
            }

            return (T) extractValue(type, response);

        } catch (WebDriverException e) {
            throw new JavascriptInvocationFailedException(format("Failed invoking expression:\n", expression), e);
        }
    }

    protected void loadPartitionedScriptsAndStyles(JavascriptExecutor wd) {
        List<String> jsChunks = Splitter.fixedLength(CHUNK_SIZE).splitToList(jsScripts);
        List<String> styleChunks = styles == null ?
                ImmutableList.<String>of() :
                Splitter.fixedLength(CHUNK_SIZE).splitToList(styles);

        for (String jsChunk : jsChunks) {
            wd.executeScript(loadJsStylesScript(), jsChunk, "");
        }

        for (String styleChunk : styleChunks) {
            wd.executeScript(loadJsStylesScript(), "", styleChunk);
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
        List<Object> fullArgs = Lists.<Object>newArrayList(jsScripts, styles, expr);
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

    protected String loadJsStylesScript() {
        return Joiner.on("; ").join(ARGS_DECLARATION, loadJsStylesTemplate);
    }
    protected String fullInvokerScript() {
        return Joiner.on("; ").join(ARGS_DECLARATION, loadJsStylesTemplate, setMiniumVarTemplate, evalExpressionTemplate);
    }

    protected Collection<String> getJsResources() {
        return jsResources;
    }

    protected Collection<String> getCssResources() {
        return cssResources;
    }

    protected String getJsContent(String filePath) {
        return ResourceFunctions.classpathFileToStringFunction(classLoader).apply(filePath).replaceAll("\r?\n", " ");
    }

    protected String combineResources(Collection<String> resources) {
        return Joiner.on("\n\n").join(Collections2.transform(resources, ResourceFunctions.classpathFileToStringFunction(classLoader)));
    }

}
