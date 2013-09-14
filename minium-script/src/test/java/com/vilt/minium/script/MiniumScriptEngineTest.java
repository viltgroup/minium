package com.vilt.minium.script;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.NativeFunction;
import org.testng.annotations.Test;

public class MiniumScriptEngineTest {

	@Test
	public void testEngineWebElementDrivers() throws Exception {
		// given
		MiniumScriptEngine engine = new MiniumScriptEngine(WebElementsDrivers.instance());
		
		// when
		Object firefoxDriver = engine.eval("firefoxDriver");
		Object load = engine.eval("load");
		
		// then
		assertThat(firefoxDriver, instanceOf(NativeFunction.class));
		assertThat(load, instanceOf(FunctionObject.class));
	}
	
	@Test
	public void testEnginePut() throws Exception {
		// given
		MiniumScriptEngine engine = new MiniumScriptEngine();
		engine.put("foo", "bar");
		
		// when
		Object result = engine.eval("foo");
		
		// then
		assertThat(result, instanceOf(String.class));
		assertThat((String) result, equalTo("bar"));
	}
}
