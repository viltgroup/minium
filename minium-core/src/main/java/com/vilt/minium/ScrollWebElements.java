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
package com.vilt.minium;

/**
 * Makes available javascript functions for element scrolling
 * (<a href="https://developer.mozilla.org/en-US/docs/DOM/element.scrollIntoView">
 * element.scrollIntoView</a>)
 *
 * @param <T> the generic type
 * @author Rui
 */
@JQueryResources("minium/js/scroll.js")
public interface ScrollWebElements extends WebElements {
	
	/**
	 * Scrolls the element into view. By default, the element is scrolled to align 
	 * with the top of the scroll area.
	 */
	public void scrollIntoView();
	
	/**
	 * Scrolls the element into view.
	 * 
	 * @param alignWithTop If <code>true</code>, the scrolled element is aligned with 
	 * the top of the scroll area. If <code>false</code>, it is aligned with the bottom.
	 */
	public void scrollIntoView(boolean alignWithTop);
}
