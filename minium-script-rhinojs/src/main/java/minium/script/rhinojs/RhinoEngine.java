package minium.script.rhinojs;

import static minium.internal.Paths.toURL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import minium.internal.Paths;
import minium.script.rhinojs.RhinoProperties.RequireProperties;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Wrapper;
import org.mozilla.javascript.tools.shell.Global;
import org.openqa.selenium.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class RhinoEngine implements JsEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(RhinoEngine.class);

    private static final Function<String, String> TO_URI_FN = new Function<String, String>() {

        @Override
        public String apply(String input) {
            try {
                return toURL(input).toString();
            } catch (Exception e) {
                return new File(input).toURI().toString();
            }
        }
    };

    abstract class RhinoCallable<T, X extends Exception> implements Callable<T> {
        @Override
        public T call() throws Exception {
            Context cx = Context.enter();
            try {
                return doCall(cx);
            } finally {
                Context.exit();
            }
        }

        protected abstract T doCall(Context cx) throws X;
    }

    private final Scriptable scope;

    public <T> RhinoEngine(final RhinoProperties properties) {
        // this ensures a single thread for this engine
        scope = runWithContext(new RhinoCallable<Scriptable, RuntimeException>() {
            @Override
            protected Scriptable doCall(Context cx) {
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
        FileReader reader = new FileReader(sourceFile);
        try {
            return runScript(reader, sourceFile.getPath());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#runScript(java.lang.String)
     */
    @Override
    public <T> T runScript(String path) throws IOException {
        Reader reader = null;
        try {
            URL url = Paths.toURL(path);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            return runScript(reader, url.getPath());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#runScript(java.io.Reader, java.lang.String)
     */
    @Override
    public <T> T runScript(final Reader reader, final String sourceName) throws IOException {
        return runWithContext(new RhinoCallable<T, IOException>() {
            @SuppressWarnings("unchecked")
            @Override
            protected T doCall(Context cx) throws IOException {
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
    public <T> T eval(final String expression) {
        return runWithContext(new RhinoCallable<T, RuntimeException>() {
            @SuppressWarnings("unchecked")
            @Override
            protected T doCall(Context cx) {
                Object val = cx.evaluateString(scope, expression, "<expression>", 1, null);
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
            protected Boolean doCall(Context cx) {
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
            protected Object doCall(Context cx) {
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
            protected Void doCall(Context cx) {
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
            protected Void doCall(Context cx) {
                scope.delete(varName);
                return null;
            }
        });
    }

    protected Object unwrappedValue(Object val) {
        if (val != null && val instanceof Wrapper) {
            val = ((Wrapper) val).unwrap();
        }
        return val;
    }

    @SuppressWarnings("unchecked")
    protected <T, X extends Exception> T runWithContext(RhinoCallable<? extends T, X> fn) throws X {
        try {
            return fn.call();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw Throwables.propagate(e);
        } catch (Exception e) {
            throw (X) e;
        }
    }

    protected List<String> getModulePathURIs(RequireProperties properties) {
       return Lists.transform(properties.getModulePaths(), TO_URI_FN);
    }
}
