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
