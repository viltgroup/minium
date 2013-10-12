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

import static com.google.common.base.Preconditions.checkNotNull;

import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.WrappedException;
import org.openqa.selenium.WebDriverException;

import com.google.common.base.Objects;
import com.vilt.minium.WebElementsException;

public class EvalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String sourceName;
	private int lineNumber = -1;

	public EvalException(Throwable exception) {
       super(getBaseException(exception));
	    
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
	}

	
	public int getLineNumber() {
        return lineNumber;
    }
	
	public String getSourceName() {
        return sourceName;
    }

	private static Throwable getBaseException(Throwable exception) {
	    checkNotNull(exception);
	    
	    if (exception instanceof WrappedException) {
	        exception = ((WrappedException) exception).getWrappedException();
	    }
	    
	    if (exception instanceof WebDriverException) {
	        exception = new WebElementsException(exception);
	    }
	    return exception;
	}
}
