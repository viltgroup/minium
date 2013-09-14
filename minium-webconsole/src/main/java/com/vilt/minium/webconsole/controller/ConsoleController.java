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

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.script.MiniumScriptEngine;

@Controller
@Scope(SCOPE_SESSION)
@RequestMapping("/console")
public class ConsoleController {

	private static final Logger logger = LoggerFactory.getLogger(ConsoleController.class);
	
	private MiniumScriptEngine engine;

	public ConsoleController(MiniumScriptEngine engine) {
		this.engine = engine;
	}
	
	@RequestMapping(value = "/eval", method = { POST, GET })
	@ResponseBody
	public synchronized EvalResult eval(@RequestParam("expr") final String expression) {
		return execute(expression, new Callable<Object>() { @Override public Object call() throws Exception {
			return engine.eval(expression);
		}});
	}

	protected EvalResult execute(String toDisplay, Callable<?> callable) {
		try {
			Object result = callable.call();
			if (result instanceof DebugWebElements) {
				DebugWebElements webElements = (DebugWebElements) result;
				int count = webElements.highlightAndCount();
				return new EvalResult(toDisplay, count);
			}
			else {
				return new EvalResult(result);
			}
		} 
		catch(Exception e) {
			logger.error("Evaluation of {} failed", toDisplay, e);
			return new EvalResult(e);
		}
	}
}
