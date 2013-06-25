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

@JQueryResources("minium/js/condition.js")
public interface ConditionWebElements<T extends CoreWebElements<T>> extends WebElements {

	public T whenNotEmpty(String selector);
	
	public T whenNotEmpty(T elems);
	
	public T whenEmpty(String elems);

	public T whenEmpty(T elems);

	public T notEmptyThen(String selector);
	
	public T notEmptyThen(T elems);
	
	public T emptyThen(String elems);
	
	public T emptyThen(T elems);
}
