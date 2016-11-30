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
package minium.script.rhinojs;

import static minium.internal.Paths.toURLs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import minium.Elements;
import minium.internal.Paths;
import minium.script.js.JsEngine;
import minium.script.rhinojs.RhinoProperties.RequireProperties;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.WrapFactory;
import org.mozilla.javascript.Wrapper;
import org.mozilla.javascript.json.JsonParser;
import org.mozilla.javascript.json.JsonParser.ParseException;
import org.mozilla.javascript.tools.shell.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class RhinoEngine implements JsEngine, DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RhinoEngine.class);

    private static final ThreadFactory FACTORY = new ThreadFactoryBuilder().setNameFormat("jsengine-thread-%d").setDaemon(true).build();

    public abstract class RhinoCallable<T, X extends Exception> implements Callable<T> {

        @Override
        public T call() throws X {
            Context cx = Context.enter();
            cx.setWrapFactory(wrapFactory);
            try {
                return doCall(cx, scope);
            } finally {
                Context.exit();
            }
        }

        protected abstract T doCall(Context cx, Scriptable scope) throws X;
    }

    private WrapFactory wrapFactory = new WrapFactory() {

        @Override
        public Object wrap(Context cx, Scriptable scope, Object obj, Class<?> staticType) {
            final Object ret = super.wrap(cx, scope, obj, staticType);
            if (ret instanceof Scriptable) {
                final Scriptable sret = (Scriptable) ret;
                if (sret.getPrototype() == null) {
                    if (obj instanceof Elements) {
                        sret.setPrototype(prototype);
                    } else {
                        sret.setPrototype(new NativeObject());
                    }
                }
            }
            return ret;
        }
    };
    private Thread executionThread;
    private ExecutorService executorService;
    private Future<?> lastTask;
    private Scriptable scope;
    private Scriptable prototype;

    public <T> RhinoEngine(final RhinoProperties rhinoProperties) {
        this.executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Preconditions.checkState(executionThread == null, "Only one thread is supported");
                executionThread = FACTORY.newThread(r);
                return executionThread;
            }
        });

        // this ensures a single thread for this engine
        runWithContext(new RhinoCallable<Void, RuntimeException>() {
            @Override
            protected Void doCall(Context cx, Scriptable s) {
                try {
                    Global global = new Global(cx);
                    RequireProperties require = rhinoProperties.getRequire();
                    if (require != null) {
                        List<String> modulePathURIs = getModulePathURIs(require);
                        LOGGER.debug("Module paths: {}", modulePathURIs);
                        global.installRequire(cx, modulePathURIs, require.isSandboxed());
                    }
                    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                    // we need to load compat/timeout.js because rhino does not have setTimeout, setInterval, etc.
                    try (Reader in = new InputStreamReader(classloader.getResourceAsStream("compat/timeout.js"))) {
                        cx.evaluateReader(global, in, "compat/timeout.js", 1, null);
                    }
                    scope = global;
                    prototype = new NativeObject();

                    scope.put("__prototype", scope, prototype);

                    return null;
                } catch (IOException e) {
                    throw Throwables.propagate(e);
                }
            }
        });
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#runScript(java.io.File)
     */
    @Override
    public <T> T runScript(File sourceFile) throws IOException {
        try (FileReader reader = new FileReader(sourceFile)) {
            return runScript(reader, sourceFile.getPath());
        }
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#runScript(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T runScript(String path) throws IOException {
        List<URL> urls = Paths.toURLs(path);
        Object result = null;
        for (URL url : urls) {
            try (Reader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                result = runScript(reader, url.getPath());
            }
        }
        return (T) result;
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#runScript(java.io.Reader, java.lang.String)
     */
    @Override
    public <T> T runScript(final Reader reader, final String sourceName) throws IOException {
        return runWithContext(new RhinoCallable<T, IOException>() {
            @SuppressWarnings("unchecked")
            @Override
            protected T doCall(Context cx, Scriptable scope) throws IOException {
                Object val = cx.evaluateReader(scope, reader, sourceName, 1, null);
                val = unwrappedValue(val);
                return (T) val;
            }
        });
    }

    @Override
    public <T> T eval(final String expression, final int line) {
        return eval(expression, "<expression>", line);
    }

    @Override
    public <T> T eval(final String expression, final String filePath, final int line) {
        return runWithContext(new RhinoCallable<T, RuntimeException>() {
            @SuppressWarnings("unchecked")
            @Override
            protected T doCall(Context cx, Scriptable scope) {
                Object val = cx.evaluateString(scope, expression, filePath, line, null);
                val = unwrappedValue(val);
                return (T) val;
            }
        });
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#contains(java.lang.String)
     */
    @Override
    public boolean contains(final String varName) {
        return runWithContext(new RhinoCallable<Boolean, RuntimeException>() {
            @Override
            protected Boolean doCall(Context cx, Scriptable scope) {
                return scope.get(varName, scope) != null;
            }
        });
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#get(java.lang.String)
     */
    @Override
    public Object get(final String varName) {
        return runWithContext(new RhinoCallable<Object, RuntimeException>() {
            @Override
            protected Object doCall(Context cx, Scriptable scope) {
                return unwrappedValue(scope.get(varName, scope));
            }
        });
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#get(java.lang.String, java.lang.Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(final String varName, Class<T> clazz) {
        return (T) get(varName);
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#put(java.lang.String, java.lang.Object)
     */
    @Override
    public void put(final String varName, final Object object) {
        runWithContext(new RhinoCallable<Void, RuntimeException>() {
            @Override
            protected Void doCall(Context cx, Scriptable scope) {
                scope.put(varName, scope, object != null ? Context.javaToJS(object, scope) : null);
                return null;
            }
        });
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#delete(java.lang.String)
     */
    @Override
    public void delete(final String varName) {
        runWithContext(new RhinoCallable<Void, RuntimeException>() {
            @Override
            protected Void doCall(Context cx, Scriptable scope) {
                scope.delete(varName);
                return null;
            }
        });
    }

    @Override
    public void putJson(final String varName, final String json) {
        runWithContext(new RhinoCallable<Object, RuntimeException>() {
            @Override
            protected Object doCall(Context cx, Scriptable scope) throws RuntimeException {
                try {
                    Object obj = new JsonParser(cx, scope).parseValue(json);
                    scope.put(varName, scope, obj);
                    return null;
                } catch (ParseException e) {
                    throw Throwables.propagate(e);
                }
            }
        });
    }

    @Override
    public boolean isRunning() {
        return lastTask != null && !lastTask.isDone();
    }

    @Override
    public void cancel() {
        if (lastTask != null) {
            lastTask.cancel(true);
        }
    }

    @Override
    public StackTraceElement[] getExecutionStackTrace() {
        if (lastTask != null && !lastTask.isDone()) {
            return filterStackTrace(executionThread.getStackTrace());
        } else {
            return new StackTraceElement[0];
        }
    }

    @Override
    public String toString(final Object obj) {

        return runWithContext(new RhinoCallable<String, RuntimeException>() {
            @Override
            protected String doCall(Context cx, Scriptable scope) throws RuntimeException {
                if (obj == null) {
                    return "null";
                } else if (obj instanceof Wrapper) {
                    Object unwrapped = ((Wrapper) obj).unwrap();
                    return unwrapped.toString();
                } else if (obj instanceof Scriptable) {
                    return (String) cx.evaluateString((Scriptable) obj,
                            "(function(obj) { " +
                            "  try { " +
                            "    var str = JSON.stringify(obj); " +
                            "    if (typeof str === 'string') return str; " +
                            "  } catch (e) { } " +
                            "  return obj.toString(); " +
                            "})(this)",
                            "<toString>", 1, null);

                } else if (obj instanceof Undefined) {
                    return "undefined";
                }
                return obj.toString();
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
    }

    @SuppressWarnings("unchecked")
    public <T, X extends Exception> T runWithContext(RhinoCallable<? extends T, X> fn) throws X {
        if (Thread.currentThread() == executionThread) {
            Context currentContext = Context.getCurrentContext();
            Preconditions.checkState(currentContext != null, "A rhino context was expected at this thread");
                return fn.doCall(currentContext, scope);
        }

        Preconditions.checkState(lastTask == null || lastTask.isDone());
        try {
            this.lastTask = executorService.submit(fn);
            return (T) this.lastTask.get();
        } catch (InterruptedException e) {
            this.lastTask.cancel(true);
            Thread.currentThread().interrupt();
            throw Throwables.propagate(e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            Throwables.propagateIfPossible(cause);
            throw (X) cause;
        }
    }

    protected StackTraceElement[] filterStackTrace(StackTraceElement[] stackTrace) {
        List<StackTraceElement> processed = Lists.newArrayList();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().startsWith("org.mozilla.javascript.gen") && element.getLineNumber() != -1) {
                String fileName = null;
                File file = new File(element.getFileName());
                if (file.exists() && file.isFile()) {
                    fileName = file.getAbsolutePath();
                }
                if (fileName == null) fileName = element.getFileName();
                processed.add(new StackTraceElement(element.getFileName(), element.getMethodName(), fileName, element.getLineNumber()));
            }
        }
        return processed.toArray(new StackTraceElement[processed.size()]);
    }

    protected Object unwrappedValue(Object val) {
        if (val != null && val instanceof Wrapper) {
            val = ((Wrapper) val).unwrap();
        }
        return val;
    }

    protected List<String> getModulePathURIs(RequireProperties properties) {
        List<String> uris = Lists.newArrayList();
        for (String modulePath : properties.getModulePaths()) {
            try {
                List<URL> urls = toURLs(modulePath);
                for (URL url : urls) {
                    uris.add(url.toString());
                }
            } catch (Exception e) {
                uris.add(new File(modulePath).toURI().toString());
            }
        }
        return uris;
    }
}
