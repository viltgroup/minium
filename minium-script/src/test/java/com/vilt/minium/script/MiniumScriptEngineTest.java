package com.vilt.minium.script;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.net.URL;

import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.NativeFunction;
import org.testng.annotations.Test;

public class MiniumScriptEngineTest {

	@Test
	public void testEngineWebElementDrivers() throws Exception {
		// given
		MiniumScriptEngine engine = new MiniumScriptEngine(WebElementsDriverFactory.instance());
		
		// when
		Object firefoxDriver = engine.eval("firefoxDriver");
		Object load = engine.eval("load");
		
		// then
		assertThat(firefoxDriver, instanceOf(NativeFunction.class));
		assertThat(load, instanceOf(FunctionObject.class));
	}

	@Test
	public void testEngineDefaultConst() throws Exception {
	    // given
	    MiniumScriptEngine engine = new MiniumScriptEngine();
	    
	    // when
	    Object firefoxDriver = engine.eval("firefoxDriver");
	    
	    // then
	    assertThat(firefoxDriver, instanceOf(NativeFunction.class));
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
	
	@Test
	public void testNativeJavaObjects() throws Exception {
	    // given
	    MiniumScriptEngine engine = new MiniumScriptEngine();
	    
	    // when
	    Object result = engine.eval("java.lang.Integer.valueOf(1)");
	    
	    // then
	    assertThat(result, instanceOf(Integer.class));
	}

	@Test
	public void testRunScript() throws Exception {
	    // given
	    MiniumScriptEngine engine = new MiniumScriptEngine();
	    
	    // when
	    URL resource = getClass().getClassLoader().getResource("test.js");
	    engine.run(new File(resource.getFile()));
	    
	    // then
	    assertThat((String) engine.get("foo"), equalTo("bar"));
	}
}
