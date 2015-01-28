package minium.script.dynjs;

import static minium.internal.Paths.toURL;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import minium.internal.Paths;
import minium.script.dynjs.DynJsProperties.RequireProperties;

import org.apache.commons.io.input.ReaderInputStream;
import org.dynjs.Config;
import org.dynjs.runtime.DynJS;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.JSObject;
import org.dynjs.runtime.builtins.Require;
import org.openqa.selenium.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class DynJsEngine implements JsEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynJsEngine.class);

    private static final Function<String, String> TO_URI_FN = new Function<String, String>() {

        @Override
        public String apply(String input) {
            try {
                URL url = toURL(input);
                if ("file".equals(url.getProtocol())) {
                    return url.getFile();
                } else {
                    return url.toString();
                }
            } catch (Exception e) {
                return new File(input).getAbsolutePath();
            }
        }
    };
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
    @SuppressWarnings("unchecked")
    public <T> T runScript(String path) throws IOException {
        InputStream is = null;
        try {
            URL url = Paths.toURL(path);
            is = url.openStream();
            return (T) dynjs.evaluate(is);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#runScript(java.io.Reader, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T runScript(final Reader reader, final String sourceName) throws IOException {
        return (T) dynjs.evaluate(new ReaderInputStream(reader));
    }

    /* (non-Javadoc)
     * @see minium.script.rhinojs.JsEngine#eval(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T eval(final String expression) {
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

    protected List<String> getModulePathURIs(RequireProperties properties) {
       return Lists.transform(properties.getModulePaths(), TO_URI_FN);
    }
}
