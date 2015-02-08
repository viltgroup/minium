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

import minium.internal.Paths;
import minium.script.js.JsEngine;
import minium.script.rhinojs.RhinoProperties.RequireProperties;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
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

    private static final ThreadFactory FACTORY = new ThreadFactoryBuilder().setNameFormat("jsengine-thread-%d").build();

    public abstract class RhinoCallable<T, X extends Exception> implements Callable<T> {

        @Override
        public T call() throws Exception {
            Context cx = Context.enter();
            try {
                return doCall(cx, scope);
            } finally {
                Context.exit();
            }
        }

        protected abstract T doCall(Context cx, Scriptable scope) throws X;
    }

    private Thread executionThread;
    private ExecutorService executorService;
    private Future<?> lastTask;
    private final Scriptable scope;

    public <T> RhinoEngine(final RhinoProperties properties) {
        this.executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Preconditions.checkState(executionThread == null, "Only one thread is supported");
                executionThread = FACTORY.newThread(r);
                return executionThread;
            }
        });
        // this ensures a single thread for this engine
        scope = runWithContext(new RhinoCallable<Scriptable, RuntimeException>() {
            @Override
            protected Scriptable doCall(Context cx, Scriptable scope) {
                Global global = new Global(cx);
                RequireProperties require = properties.getRequire();
                if (require != null) {
                    List<String> modulePathURIs = getModulePathURIs(require);
                    LOGGER.debug("Module paths: {}", modulePathURIs);
                    global.installRequire(cx, modulePathURIs, require.isSandboxed());
                }
                return global;
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

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#eval(java.lang.String)
     */
    @Override
    public <T> T eval(final String expression, final int line) {
        return runWithContext(new RhinoCallable<T, RuntimeException>() {
            @SuppressWarnings("unchecked")
            @Override
            protected T doCall(Context cx, Scriptable scope) {
                Object val = cx.evaluateString(scope, expression, "<expression>", line, null);
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
            return process(executionThread.getStackTrace());
        } else {
            return new StackTraceElement[0];
        }
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
    }

    @SuppressWarnings("unchecked")
    public <T, X extends Exception> T runWithContext(RhinoCallable<? extends T, X> fn) throws X {
        Preconditions.checkState(lastTask == null || lastTask.isDone());
        try {
            this.lastTask = executorService.submit(fn);
            return (T) this.lastTask.get();
        } catch (InterruptedException e) {
            this.lastTask.cancel(true);
            Thread.currentThread().interrupt();
            throw Throwables.propagate(e);
        } catch (ExecutionException e) {
            throw (X) Throwables.propagate(e);
        }
    }

    protected StackTraceElement[] process(StackTraceElement[] stackTrace) {
        List<StackTraceElement> processed = Lists.newArrayList();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().startsWith("org.mozilla.javascript.gen") && element.getLineNumber() != -1) {
                String fileName = null;
                File file = new File(element.getFileName());
                if (file.exists() && file.isFile()) {
                    fileName = file.getAbsolutePath();
                }
                if (fileName == null) fileName = element.getFileName();
                processed.add(new StackTraceElement(element.getClassName(), element.getMethodName(), fileName, element.getLineNumber()));
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
