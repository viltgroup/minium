package com.vilt.minium;

/**
 * 
 * @author Rui
 *
 */
public class MiniumException extends RuntimeException {

	private static final long serialVersionUID = -8909663978475197389L;

	/**
	 * 
	 */
	public MiniumException() {
		super();
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public MiniumException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message
	 */
	public MiniumException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause
	 */
	public MiniumException(Throwable cause) {
		super(cause);
	}
}
