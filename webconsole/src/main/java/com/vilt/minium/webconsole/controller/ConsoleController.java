package com.vilt.minium.webconsole.controller;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Iterables;
import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.webconsole.factories.WebDriverFactory;

@Controller
@Scope(SCOPE_SINGLETON)
@RequestMapping("/console")
public class ConsoleController implements InitializingBean, DisposableBean {

	private static final String RHINO_MINIUM_JS = "rhino/minium.js";
	
	@Autowired
	private WebDriverFactory webDriverFactory;
	
	private ScriptEngine engine;

	private String scriptsDir;

	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
	@Override
	public void destroy() throws Exception {
	}
	
	@RequestMapping(value = "/eval", method = { POST, GET })
	@ResponseBody
	public synchronized EvalResult eval(@RequestParam("expr") String expression) {
		return doEval(expression);
	}

	@RequestMapping(value = "/evalScript", method = POST)
	@ResponseBody
	public synchronized EvalResult evalScript(@RequestBody String script) {
		return doEval(script);
	}
	
	public synchronized void scriptsDir(String scriptsDir) {
		this.scriptsDir = scriptsDir;
	}
	
	public synchronized void load(String path) {
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
			IOUtils.closeQuietly(reader);
		}
	}
	
	
	protected EvalResult doEval(String expression) {
		System.out.printf("Evaluating %s\n", expression);
		
		try {
			if (engine == null) initScope();
			
			Object result = engine.eval(expression);
			if (result instanceof DebugWebElements) {
				DebugWebElements debugWebElements = (DebugWebElements) result;
				debugWebElements.highlight();
				return new EvalResult(expression, Iterables.size(debugWebElements));
			}
			else {
				return new EvalResult(result);
			}
		}
		catch(Exception e) {
			return new EvalResult(e);
		}
	}

	protected void initScope() {
		try {
			engine = new ScriptEngineManager().getEngineByName("js");
			engine.put("_webDriverFactory", webDriverFactory);
			engine.put("_controller", this);
			engine.eval(elementsReader());
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}

	private static Reader elementsReader() {
		return new BufferedReader(new InputStreamReader(ConsoleController.class.getClassLoader().getResourceAsStream(RHINO_MINIUM_JS)));
	}

}