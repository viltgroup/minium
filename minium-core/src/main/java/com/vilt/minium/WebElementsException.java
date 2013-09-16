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

import org.openqa.selenium.WebDriverException;

/**
 * The Class WebElementsException.
 *
 * @author Rui
 */
public class WebElementsException extends RuntimeException {

	private static final long serialVersionUID = -8909663978475197389L;

	/**
	 * Instantiates a new minium exception.
	 */
	public WebElementsException() {
		super();
	}

	/**
	 * Instantiates a new minium exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public WebElementsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new minium exception.
	 *
	 * @param message the message
	 */
	public WebElementsException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new minium exception.
	 *
	 * @param cause the cause
	 */
	public WebElementsException(Throwable cause) {
		super(getMessage(cause), cause);
	}

    protected static String getMessage(Throwable cause) {
        if (cause == null) return null;
        if (!(cause instanceof WebDriverException)) return cause.getMessage();
        
        WebDriverException wdCause = (WebDriverException) cause;
        
        // we don't really want all that supporUrl and build stuff into our message
        // so let's remove that
        String message = wdCause.getMessage();
        int idx = message.indexOf("\nFor documentation on this error, please visit:");
        if (idx < 0) idx = message.indexOf("\nBuild info:");
        
        return idx < 0 ? message : message.substring(0, idx);
    }
}
