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
package com.vilt.minium.webconsole.controller;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Iterables;
import com.google.common.io.Closeables;
import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.webconsole.factories.WebDriverFactory;

@Controller
@Scope(SCOPE_SINGLETON)
@RequestMapping("/console")
public class ConsoleController {

	private static final Logger logger = LoggerFactory.getLogger(ConsoleController.class);
	
	private static final String RHINO_BOOTSTRAP_JS = "rhino/bootstrap.js";
	private static final String BOOTSTRAP_EXTS_JS = "rhino/bootstrap-extension.js";
	
	@Autowired
	private WebDriverFactory webDriverFactory;
	private ScriptEngine engine;
	private String scriptsDir;
	
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
			try { Closeables.close(reader, true); } catch (IOException e) { }
		}
	}
	
	
	protected EvalResult doEval(String expression) {
		logger.debug("Evaluating {}", expression);
		
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
			logger.error("Evaluation of {} failed", expression, e);
			return new EvalResult(e);
		}
	}

	protected void initScope() throws IOException {
		try {
			engine = new ScriptEngineManager().getEngineByName("js");
			engine.put("_webDriverFactory", webDriverFactory);
			engine.put("_controller", this);

			logger.debug("Loading minium bootstrap file");
			engine.eval(resourceFileReader(RHINO_BOOTSTRAP_JS));
			
			Enumeration<URL> resources = ConsoleController.class.getClassLoader().getResources(BOOTSTRAP_EXTS_JS);
			
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

	private static Reader resourceFileReader(String resourceName) {
		InputStream is = ConsoleController.class.getClassLoader().getResourceAsStream(resourceName);
		if (is == null) return null;
		return new BufferedReader(new InputStreamReader(is));
	}

	private static Reader resourceUrlReader(URL resourceUrl) {
		try {
			InputStream is = resourceUrl.openStream();
			return new BufferedReader(new InputStreamReader(is));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
