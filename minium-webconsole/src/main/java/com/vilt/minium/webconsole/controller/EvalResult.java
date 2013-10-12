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

import java.io.Serializable;

import com.vilt.minium.WebElements;

public class EvalResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object value;
	private int size = -1;

	public EvalResult(Object value) {
		this(value, -1);
	}
	
	public EvalResult(Object value, int size) {
		this.value = value;
		this.size = size;
		if (value instanceof WebElements) {
			this.value = ((WebElements) value).toString();
		}
		else {
			this.value = value == null ? null : value.toString();
		}
	}
	
	public Object getValue() {
		return value;
	}
	
	public int getSize() {
		return size;
	}
}
