package minium.script.dynjs;

import static minium.internal.Paths.toURLs;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import minium.internal.Paths;
import minium.script.dynjs.DynJsProperties.RequireProperties;
import minium.script.js.JsEngine;

import org.dynjs.Config;
import org.dynjs.runtime.DynJS;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.JSObject;
import org.dynjs.runtime.builtins.Require;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class DynJsEngine implements JsEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynJsEngine.class);

    private final DynJS dynjs;
    private final Config config;
    private ExecutionContext executionContext;

    public <T> DynJsEngine(final DynJsProperties properties) {
        config = new Config();
        dynjs  = new DynJS(config);
        executionContext = dynjs.getDefaultExecutionContext();
        RequireProperties requireProperties = properties.getRequire();
        if (requireProperties != null) {
          List<String> modulePathURIs = getModulePathURIs(requireProperties);
          LOGGER.debug("Module paths: {}", modulePathURIs);
          Require require = (Require) executionContext.resolve("require").getValue(executionContext);
          for (String modulePathURI : modulePathURIs) {
              require.addLoadPath(modulePathURI);
          }
        }
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
    @Override
    @SuppressWarnings("unchecked")
    public <T> T runScript(String path) throws IOException {
        List<URL> urls = Paths.toURLs(path);
        Object result = null;
        for (URL url : urls) {
            try (InputStream is = url.openStream()) {
                result = dynjs.evaluate(is);
            }
        }
        return (T) result;
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#runScript(java.io.Reader, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T runScript(final Reader reader, final String sourceName) throws IOException {
        return (T) dynjs.newRunner().withContext(dynjs.getDefaultExecutionContext()).withSource(reader).evaluate();
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#eval(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T eval(final String expression, int line) {
        return (T) dynjs.evaluate(expression);
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#contains(java.lang.String)
     */
    @Override
    public boolean contains(final String varName) {
        JSObject object = dynjs.getGlobalContext().getObject();
        return object.hasProperty(executionContext, varName);
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#get(java.lang.String)
     */
    @Override
    public Object get(final String varName) {
        JSObject object = dynjs.getGlobalContext().getObject();
        return object.getProperty(executionContext, varName);
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#get(java.lang.String, java.lang.Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(final String varName, Class<T> clazz) {
        JSObject object = dynjs.getGlobalContext().getObject();
        return (T) object.getProperty(executionContext, varName);
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#put(java.lang.String, java.lang.Object)
     */
    @Override
    public void put(final String varName, final Object object) {
        JSObject jsObject = dynjs.getGlobalContext().getObject();
        jsObject.put(executionContext, varName, object, true);
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#delete(java.lang.String)
     */
    @Override
    public void delete(final String varName) {
        JSObject jsObject = dynjs.getGlobalContext().getObject();
        jsObject.delete(executionContext, varName, true);
    }

    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    protected List<String> getModulePathURIs(RequireProperties properties) {
        List<String> uris = Lists.newArrayList();
        for (String modulePath : properties.getModulePaths()) {
            try {
                List<URL> urls = toURLs(modulePath);
                for (URL url : urls) {
                    if ("file".equals(url.getProtocol())) {
                        uris.add(url.getFile());
                    } else {
                        uris.add(url.toString());
                    }

                }
            } catch (Exception e) {
                uris.add(new File(modulePath).getAbsolutePath());
            }
        }
        return uris;
    }

    @Override
    public void putJson(String varName, String json) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public StackTraceElement[] getExecutionStackTrace() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void cancel() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean isRunning() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public String toString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }

    @Override
    public void destroy() throws Exception {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
