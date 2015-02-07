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
package minium.cucumber;

import gherkin.formatter.model.Step;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.regexp.NativeRegExp;
import org.mozilla.javascript.tools.shell.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;

import cucumber.runtime.Backend;
import cucumber.runtime.CucumberException;
import cucumber.runtime.Glue;
import cucumber.runtime.UnreportedStepExecutor;
import cucumber.runtime.io.Resource;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.rhino.JavaScriptSnippet;
import cucumber.runtime.rhino.RhinoHookDefinition;
import cucumber.runtime.rhino.RhinoStepDefinition;
import cucumber.runtime.snippets.FunctionNameGenerator;
import cucumber.runtime.snippets.SnippetGenerator;

public class MiniumBackend implements Backend {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiniumBackend.class);

	private static final String JS_DSL = "/cucumber/runtime/rhino/dsl.js";
	private final SnippetGenerator snippetGenerator = new SnippetGenerator(new JavaScriptSnippet());
	private final ResourceLoader resourceLoader;
	private final Context cx;
	private final Scriptable scope;
	private Glue glue;
	private Function buildWorldFn;
	private Function disposeWorldFn;

    public MiniumBackend(ResourceLoader resourceLoader, Context cx, Scriptable scope) throws IOException {
        try {
            this.resourceLoader = resourceLoader;
            this.cx = cx;
            this.scope = scope;
            this.scope.put("jsBackend", this.scope, this);
            InputStreamReader dsl = new InputStreamReader(getClass().getResourceAsStream(JS_DSL), Charsets.UTF_8.toString());
            this.cx.evaluateReader(this.scope, dsl, JS_DSL, 1, null);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void loadGlue(Glue glue, List<String> gluePaths) {
        this.glue = glue;
        for (String gluePath : gluePaths) {
            // JavaBackend doesn't fail with unexisting java packages (their glues), but
            // RhinoBackend fails if folders don't exist...
            // TODO open issue in cucumber
            try {
                Iterable<Resource> resources = resourceLoader.resources(gluePath, ".js");
                for (Resource resource : resources) {
                    runScript(resource);
                }
            } catch (EcmaError e) {
                throw e;
            } catch (Exception e) {
                LOGGER.warn("Could not load glue {}", gluePath, e);
            }
        }
    }

    private void runScript(Resource resource) {
        try {
            cx.evaluateReader(scope, new InputStreamReader(resource.getInputStream(), "UTF-8"), resource.getAbsolutePath(), 1, null);
        } catch (IOException e) {
            throw new CucumberException("Failed to evaluate JavaScript in " + resource.getAbsolutePath(), e);
        }
    }

    @Override
    public void setUnreportedStepExecutor(UnreportedStepExecutor executor) {
        // Not used yet
    }

    @Override
    public void buildWorld() {
        if (buildWorldFn != null) buildWorldFn.call(cx, scope, scope, new Object[0]);
    }

    @Override
    public void disposeWorld() {
        try {
            if (disposeWorldFn != null) disposeWorldFn.call(cx, scope, scope, new Object[0]);
        } finally {
            buildWorldFn = null;
            disposeWorldFn = null;
        }
    }

    public void registerWorld(Function buildWorldFn, Function disposeWorldFn) {
        if (this.buildWorldFn != null) throw new CucumberException("World is already set");
        if (buildWorldFn == null) throw new CucumberException("World requires at least a build function");

        this.buildWorldFn = buildWorldFn;
        this.disposeWorldFn = disposeWorldFn;
    }

    @Override
    public String getSnippet(Step step, FunctionNameGenerator functionNameGenerator) {
        return snippetGenerator.getSnippet(step, functionNameGenerator);
    }

    private StackTraceElement jsLocation() {
        Throwable t = new Throwable();
        StackTraceElement[] stackTraceElements = t.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            String fileName = stackTraceElement.getFileName();
            boolean js = fileName == null ? false : fileName.endsWith(".js");
            if (js) {
                boolean isDsl = fileName.endsWith(JS_DSL);
                boolean hasLine = stackTraceElement.getLineNumber() != -1;
                if (!isDsl && hasLine) {
                    return stackTraceElement;
                }
            }
        }
        throw new RuntimeException("Couldn't find location for step definition");
    }

    public void addStepDefinition(Global jsStepDefinition, NativeRegExp regexp, NativeFunction bodyFunc, NativeFunction argumentsFromFunc) throws Throwable {
        StackTraceElement stepDefLocation = jsLocation();
        RhinoStepDefinition stepDefinition = new RhinoStepDefinition(cx, scope, jsStepDefinition, regexp, bodyFunc, stepDefLocation, argumentsFromFunc);
        glue.addStepDefinition(stepDefinition);
    }

    public void addBeforeHook(Function fn, String[] tags, int order, long timeoutMillis) {
        StackTraceElement stepDefLocation = jsLocation();
        RhinoHookDefinition hookDefinition = new RhinoHookDefinition(cx, scope, fn, tags, order, timeoutMillis, stepDefLocation);
        glue.addBeforeHook(hookDefinition);
    }

    public void addAfterHook(Function fn, String[] tags, int order, long timeoutMillis) {
        StackTraceElement stepDefLocation = jsLocation();
        RhinoHookDefinition hookDefinition = new RhinoHookDefinition(cx, scope, fn, tags, order, timeoutMillis, stepDefLocation);
        glue.addAfterHook(hookDefinition);
    }
}
