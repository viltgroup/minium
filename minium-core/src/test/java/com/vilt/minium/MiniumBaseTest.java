/*
 * Copyright (C) 2013 VILT Group
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
package com.vilt.minium;

import java.net.URL;

import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableWithSize;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class MiniumBaseTest {
	
	protected DefaultWebElementsDriver wd;
	
	@BeforeSuite
	public void before() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		wd = new DefaultWebElementsDriver(new PhantomJSDriver(capabilities));
	}
	
	@AfterSuite
	public void after() {
		wd.quit();
	}
	
	protected void get(String resourcePath) {
		URL resource = MiniumBaseTest.class.getClassLoader().getResource(resourcePath);
		wd.get(resource.toExternalForm());
	}
	
	public Matcher<Iterable<WebElement>> hasSize(int size) {
		return IsIterableWithSize.<WebElement>iterableWithSize(size);		
	}
}
