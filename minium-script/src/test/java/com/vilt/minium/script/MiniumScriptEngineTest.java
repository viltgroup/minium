package com.vilt.minium.script;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.Test;

public class MiniumScriptEngineTest {

	@Test
	public void testEngineWebElementDrivers() throws Exception {
		// given
		MiniumScriptEngine engine = new MiniumScriptEngine();
		
		// when
		engine.setWebElementsDrivers(WebElementDrivers.instance());
		String type = (String) engine.eval("typeof firefoxDriver");
		
		// then
		assertThat(type, equalTo("function"));
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
