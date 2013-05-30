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

/**
 * The Class MiniumException.
 *
 * @author Rui
 */
public class MiniumException extends RuntimeException {

	private static final long serialVersionUID = -8909663978475197389L;

	/**
	 * Instantiates a new minium exception.
	 */
	public MiniumException() {
		super();
	}

	/**
	 * Instantiates a new minium exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public MiniumException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new minium exception.
	 *
	 * @param message the message
	 */
	public MiniumException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new minium exception.
	 *
	 * @param cause the cause
	 */
	public MiniumException(Throwable cause) {
		super(cause);
	}
}
