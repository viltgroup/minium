package com.vilt.minium.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.Wrapper;
import org.mozilla.javascript.tools.shell.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiniumScriptEngine {

    interface ContextCallable<V, X extends Exception> {
        public V call(Context cx) throws X;
    }
    
	private static final String RHINO_BOOTSTRAP_JS = "rhino/bootstrap.js";
	private static final String BOOTSTRAP_EXTS_JS = "rhino/bootstrap-extension.js";

	private static final Logger logger = LoggerFactory.getLogger(MiniumScriptEngine.class);
	private ClassLoader classLoader;

	private WebElementsDrivers webElementsDrivers;

	private Global scope;

	public MiniumScriptEngine() {
		this(null);
	}

	public MiniumScriptEngine(WebElementsDrivers webElementsDrivers) {
		this(webElementsDrivers, MiniumScriptEngine.class.getClassLoader());
	}

	public MiniumScriptEngine(WebElementsDrivers webElementsDrivers, ClassLoader classLoader) {
		this.webElementsDrivers = webElementsDrivers;
		this.classLoader = classLoader;
		initScope();
	}

    public boolean contains(final String varName) {
        return runWithContext(new ContextCallable<Boolean, RuntimeException>() {

            @Override
            public Boolean call(Context cx) {
                return scope.get(varName) != null;
            }

        });
    }
    public Object get(final String varName) {
        return runWithContext(new ContextCallable<Object, RuntimeException>() {
            
            @Override
            public Object call(Context cx) {
                return getUnwrappedResult(scope.get(varName));
            }
            
        });
    }

	public void put(final String varName, final Object object) {
        runWithContext(new ContextCallable<Void, RuntimeException>() {

            @Override
            public Void call(Context cx) {
                scope.put(varName, scope, object);
                return null;
            }

        });
	}

	public void delete(final String varName) {
        runWithContext(new ContextCallable<Void, RuntimeException>() {

            @Override
            public Void call(Context cx) {
                scope.delete(varName);
                return null;
            }

        });
	}
	
	public void run(final File file) throws Exception {
	    logger.debug("Executing file: {}", file);
        runWithContext(new ContextCallable<Void, Exception>() {

            @Override
            public Void call(Context cx) throws Exception {
                FileReader reader = null;
                try {
                    reader = new FileReader(file);
                    cx.evaluateReader(scope, reader, file.getPath(), 1, null);
                    return null;
                } catch (Exception e) {
                    logger.error("Execution of {} failed", file.getPath(), e);
                    throw e;
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            }
        });
	}

	public Object eval(String expression) throws Exception {
	    return eval(expression, 1);
	}
	
	public Object eval(final String expression, final int lineNumber) throws Exception {
		logger.debug("Evaluating expression: {}", expression);
        return runWithContext(new ContextCallable<Object, Exception>() {
            @Override
            public Object call(Context cx) throws Exception {
                try {
                    Object result = cx.evaluateString(scope, expression, "<expression>", lineNumber, null);
                    return getUnwrappedResult(result);
                } catch (Exception e) {
                    logger.error("Evaluation of {} failed", expression, e);
                    throw e;
                }
            }
        });
	}

	protected void initScope() {
	    runWithContext(new ContextCallable<Void, RuntimeException>() {
            @Override
            public Void call(Context cx) {
                try {
                    // Global gives us access to global functions like load()
                    scope = new Global(cx);

                    scope.put("webElementsDrivers", scope, webElementsDrivers);

                    logger.debug("Loading minium bootstrap file");
                    InputStreamReader bootstrap = new InputStreamReader(classLoader.getResourceAsStream(RHINO_BOOTSTRAP_JS), "UTF-8");
                    cx.evaluateReader(scope, bootstrap, RHINO_BOOTSTRAP_JS, 1, null);

                    Enumeration<URL> resources = classLoader.getResources(BOOTSTRAP_EXTS_JS);

                    while (resources.hasMoreElements()) {
                        URL resourceUrl = resources.nextElement();
                        Reader reader = resourceUrlReader(resourceUrl);
                        if (reader != null) {
                            logger.debug("Loading extension bootstrap from '{}'", resourceUrl.toString());
                            cx.evaluateReader(scope, reader, resourceUrl.toString(), 1, null);
                        }
                    }

                    return null;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
	}
	
	protected <V, X extends Exception> V runWithContext(ContextCallable<V, X> fn) throws X {
	    Context cx = Context.enter();
        try {
            return fn.call(cx);
    	} finally {
            Context.exit();
        }
	}

	private Reader resourceUrlReader(URL resourceUrl) {
		try {
			InputStream is = resourceUrl.openStream();
			return new BufferedReader(new InputStreamReader(is));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

    private Object getUnwrappedResult(Object result) {
        if (result instanceof Undefined)
            return null;
        if (result instanceof Wrapper)
            return ((Wrapper) result).unwrap();
        return result;
    }
}
