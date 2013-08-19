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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Closeables;

public class MiniumScriptEngine {

	private static final String RHINO_BOOTSTRAP_JS = "rhino/bootstrap.js";
	private static final String BOOTSTRAP_EXTS_JS = "rhino/bootstrap-extension.js";
	
	private static final Logger logger = LoggerFactory.getLogger(MiniumScriptEngine.class);
	private ClassLoader classLoader;
	
	private ScriptEngine engine;
	private String scriptsDir;
	private WebElementDrivers webElementsDrivers;
	
	public MiniumScriptEngine() {
		this(MiniumScriptEngine.class.getClassLoader());
	}
	
	public MiniumScriptEngine(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public void setWebElementsDrivers(WebElementDrivers webElementsDrivers) {
		this.webElementsDrivers = webElementsDrivers;
	}
	
	public void scriptsDir(String scriptsDir) {
		this.scriptsDir = scriptsDir;
	}
	public Object eval(String expression) throws Exception {
		return doEval(expression);
	}

	public Object evalScript(String script) throws Exception {
		return doEval(script);
	}

	public void load(String path) {
		FileReader reader = null;
		try {
		
			File file = new File(path);
			if (!file.isAbsolute()) {
				file = new File(scriptsDir, path);
			}
			
			reader = new FileReader(file);
			engine.eval(reader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try { Closeables.close(reader, true); } catch (IOException e) { }
		}
	}
	
	protected Object doEval(String expression) throws Exception {
		logger.debug("Evaluating {}", expression);
		
		try {
			if (engine == null) initScope();
			
			Object result = engine.eval(expression);
			return result;
		}
		catch(Exception e) {
			logger.error("Evaluation of {} failed", expression, e);
			throw e;
		}
	}
	
	protected void initScope() throws IOException {
		try {
			engine = new ScriptEngineManager().getEngineByName("js");
			engine.put("webElementsDrivers", webElementsDrivers);
			engine.put("scriptEngine", this);

			logger.debug("Loading minium bootstrap file");
			engine.eval(resourceFileReader(RHINO_BOOTSTRAP_JS));
			
			Enumeration<URL> resources = classLoader.getResources(BOOTSTRAP_EXTS_JS);
			
			while(resources.hasMoreElements()) {
				URL resourceUrl = resources.nextElement();
				Reader reader = resourceUrlReader(resourceUrl);
				if (reader != null) {
					logger.debug("Loading extension bootstrap from '{}'", resourceUrl.toString());
					engine.eval(reader);
				}
			}
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Reader resourceFileReader(String resourceName) {
		InputStream is = classLoader.getResourceAsStream(resourceName);
		if (is == null) return null;
		return new BufferedReader(new InputStreamReader(is));
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
