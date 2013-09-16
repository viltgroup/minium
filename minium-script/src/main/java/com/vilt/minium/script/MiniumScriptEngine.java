package com.vilt.minium.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.tools.shell.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiniumScriptEngine {

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

	public void put(String varName, Object object) {
		scope.put(varName, scope, object);
	}

	public Object eval(String expression) throws Exception {
	    return eval(expression, 1);
	}
	
	public Object eval(String expression, int lineNumber) throws Exception {
		logger.debug("Evaluating expression: {}", expression);

		Context cx = Context.enter();
		try {
			Object result = cx.evaluateString(scope, expression, "<expression>", lineNumber, null);
			if (result instanceof Undefined) return null;
			if (result instanceof NativeJavaObject) return ((NativeJavaObject) result).unwrap();
			return result;
		} catch (Exception e) {
			logger.error("Evaluation of {} failed", expression, e);
			throw e;
		} finally {
			Context.exit();
		}
	}

	protected void initScope() {
		Context cx = Context.enter();
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
		} catch (Exception e) {
			throw new RuntimeException(e);
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
}
