/*
 * Copyright (C) 2013 The Minium Authors
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
package com.vilt.minium.impl;

import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.WebElementsException;

/**
 * This class is responsible for injecting the necessary javascript code in the
 * current page so that jQuery expressions can be evaluated.
 *
 * @author Rui
 */
public class JQueryInvoker {

    private static final Logger LOGGER = LoggerFactory.getLogger(JQueryInvoker.class);

    public interface Compressor {
        String compress(ClassLoader classLoader, Collection<String> jsResources);
    }

    private static class NullCompressor implements Compressor {
        @Override
        public String compress(ClassLoader classLoader, Collection<String> jsResources) {
            return combineResources(jsResources);
        }
    }

    private static class ClasspathFileToStringFunction implements Function<String, String> {
        @Override
        public String apply(String input) {
            return getFileContent(input);
        }
    }

    private static Compressor compressor;

    static {
        try {
            Class.forName("com.google.javascript.jscomp.Compiler", false, JQueryInvoker.class.getClassLoader());
        } catch (Exception e) {
            LOGGER.info("Google Closure Compiler not found in classpath, javascript will not be compressed");
            compressor = new NullCompressor();
        } catch (UnsupportedClassVersionError e) {
            LOGGER.info("Google Closure Compiler found in classpath but not supported by his JVM. Please consider using a more recent JVM or another Google Closure Compiler version");
            compressor = new NullCompressor();
        }
        if (compressor == null) {
            compressor = new ClosureCompressor();
        }
    }

    // resource paths
    private final Collection<String> jsResources;
    private final Collection<String> cssResources;

    // concatenated styles resources content
    private String styles;

    private String lightInvokerScriptTemplate;
    private String fullInvokerScriptTemplate;

    public JQueryInvoker(Collection<String> jsResources, Collection<String> cssResources) {
        this.jsResources = jsResources;
        this.cssResources = cssResources;

        try {
            String jsScripts = compressor.compress(JQueryInvoker.class.getClassLoader(), jsResources);

            if (cssResources != null) {
                styles = combineResources(cssResources);
            }

            lightInvokerScriptTemplate = getFileContent("minium/templates/jquery-invoker-light.template");
            fullInvokerScriptTemplate = getFileContent("minium/templates/jquery-invoker-full.template");
            fullInvokerScriptTemplate = fullInvokerScriptTemplate.replace("{{jsScript}}", jsScripts);

        } catch (Exception e) {
            throw new WebElementsException(e);
        }
    }

    public <T> T invoke(JavascriptExecutor wd, String expression, Object... args) {
        return this.<T> invoke(wd, false, expression, args);
    }

    @SuppressWarnings("unchecked")
    public <T> T invoke(JavascriptExecutor wd, boolean async, String expression, Object... args) {
        try {
            args = convertToValidArgs(args);

            Object[] fullArgs = args == null ? new Object[1] : new Object[args.length + 1];
            fullArgs[0] = async;
            if (args != null)
                System.arraycopy(args, 0, fullArgs, 1, args.length);

            Object result = async ?
                    wd.executeAsyncScript(lightInvokerScript(expression), fullArgs) :
                    wd.executeScript(lightInvokerScript(expression), fullArgs);

            if (result instanceof Boolean && !((Boolean) result).booleanValue()) {
                fullArgs = args == null ? new Object[2] : new Object[args.length + 2];
                fullArgs[0] = async;
                fullArgs[1] = styles;
                if (args != null)
                    System.arraycopy(args, 0, fullArgs, 2, args.length);
                result = async ?
                        wd.executeAsyncScript(fullInvokerScript(expression), fullArgs) :
                        wd.executeScript(fullInvokerScript(expression), fullArgs);
            }

            if (!(result instanceof List))
                throw new IllegalStateException(format("Expected a list with the result in the first position..."));

            List<?> resultAsList = (List<?>) result;
            // results can only be empty if it was a result of a jQuery evaluation
            if (resultAsList.isEmpty()) return (T) Collections.emptyList();

            if (resultAsList.get(0) instanceof WebElement) {
                // it's an array of web elements
                return (T) resultAsList;
            }

            return (T) resultAsList.get(0);
        } catch (Exception e) {
            throw new WebElementsException(e);
        }
    }

    public Object invokeExpression(WebElementsDriver<?> wd, boolean async, String expression, Object... args) {
        return invoke(wd, async, format("return %s;", expression), args);
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

    private Object[] convertToValidArgs(Object[] args) {
        // we need to get the wrapped web element from every DelegateWebElement
        // in arguments
        Object[] validArgs = new Object[args.length];
        System.arraycopy(args, 0, validArgs, 0, args.length);

        for (int i = 0; i < validArgs.length; i++) {
            Object arg = validArgs[i];
            if (arg instanceof DelegateWebElement) {
                validArgs[i] = ((DelegateWebElement) arg).getWrappedWebElement();
            } else if (arg instanceof Object[]) {
                validArgs[i] = convertToValidArgs((Object[]) arg);
            }
        }
        return validArgs;
    }

    protected static String getFileContent(String filename) {
        InputStream in = null;
        try {
            in = getClasspathFileInputStream(filename);
            return CharStreams.toString(new InputStreamReader(in, Charsets.UTF_8));
        } catch (IOException e) {
            throw new WebElementsException(format("Could not load %s from classpath", filename), e);
        } finally {
            try { Closeables.close(in, true); } catch (IOException e) { }
        }
    }

    protected static InputStream getClasspathFileInputStream(String filename) {
        return JQueryInvoker.class.getClassLoader().getResourceAsStream(filename);
    }

    protected static String combineResources(Collection<String> resources) {
        return Joiner.on("\n\n").join(Collections2.transform(resources, new ClasspathFileToStringFunction()));
    }
}
