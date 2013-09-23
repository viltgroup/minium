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
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.WrappedException;
import org.openqa.selenium.WebDriverException;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsException;

public class EvalResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object value;
	private Map<String, Object> exception;
	
	@JsonIgnore
	private Throwable realException;

	private int size = -1;
	private int lineNumber = -1;
    private String sourceName;


	public EvalResult(Object value) {
		this(value, null);
	}
	
	public EvalResult(Object value, int size) {
		this(value);
		this.size = size;
	}
	
	public EvalResult(Throwable e) {
		this(null, e);
	}
	
	public EvalResult(Object value, Throwable exception) {
		if (exception != null) {
		    if (exception instanceof RhinoException) {
		        StackTraceElement[] stackTrace = exception.getStackTrace();
		        for (StackTraceElement stackTraceElement : stackTrace) {
                    if (Objects.equal("<expression>", stackTraceElement.getFileName())) {
                        lineNumber = stackTraceElement.getLineNumber();
                        sourceName = null;
                        break;
                    }
                }
		        if (lineNumber < 0) {
		            lineNumber = ((RhinoException) exception).lineNumber();
		            sourceName = ((RhinoException) exception).sourceName();
		        }
		    }
		    
		    if (exception instanceof WrappedException) {
		        exception = ((WrappedException) exception).getWrappedException();
		    }
		    
		    if (exception instanceof WebDriverException) {
		        exception = new WebElementsException(exception);
		    }
		    
		    String message = exception.getMessage();
			
			this.realException = exception;
			this.exception = Maps.newHashMap();
			this.exception.put("class", exception.getClass().getName());
			this.exception.put("message", message);
			this.exception.put("fullStackTrace", Throwables.getStackTraceAsString(exception));
		}
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
	
	public int getLineNumber() {
        return lineNumber;
    }
	
	public String getSourceName() {
        return sourceName;
    }
	
	@JsonIgnore
	public Throwable getException() {
		return realException;
	}
	
	public Map<String, Object> getExceptionInfo() {
		return exception;
	}
}
