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
