package com.vilt.minium.rhino;

import org.testng.annotations.Test;

import com.vilt.minium.script.MiniumScriptEngine;
import com.vilt.minium.script.WebElementDrivers;

public class MiniumRhinoEngineTest {

	@Test
	public void testEngine() throws Exception {
		MiniumScriptEngine engine = new MiniumScriptEngine();
		engine.setWebElementsDrivers(WebElementDrivers.instance());
		
//		engine.eval("wd = firefoxDriver(); get(wd, 'http://www.google.com');");
	}
}
