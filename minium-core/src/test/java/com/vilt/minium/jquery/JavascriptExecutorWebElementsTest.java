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
package com.vilt.minium.jquery;

import static com.vilt.minium.JavascriptFunctions.parse;
import static com.vilt.minium.Minium.$;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vilt.minium.JavascriptFunction;
import com.vilt.minium.MiniumBaseTest;

public class JavascriptExecutorWebElementsTest extends MiniumBaseTest {

	@BeforeMethod
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test
	public void testEval() {
	    JavascriptFunction fn = parse("function(label) { return label + $(this).text(); }");
	    assertThat($(wd, "h2").eval(fn, "H2 text is: "), equalTo((Object) "H2 text is: JQuery Tests"));
	}
	
	@Test
	public void testEvalWebElements() {
	    JavascriptFunction fn = parse("function(label) { $(this).text(label + $(this).text()); return $(this); }");
	    assertThat($(wd, "h2").evalWebElements(fn, "H2 text is: ").text(), equalTo((Object) "H2 text is: JQuery Tests"));
	}
	
	@Test
	public void testEvalAsync() {
	    JavascriptFunction fn = parse("function(label, callback) { var text = $(this).text(); setTimeout(function() { callback(label + text); }, 10); }");
	    assertThat($(wd, "h2").evalAsync(fn, "H2 text is: "), equalTo((Object) "H2 text is: JQuery Tests"));
	}
}
