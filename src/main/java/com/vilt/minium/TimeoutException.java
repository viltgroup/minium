package com.vilt.minium;

public class TimeoutException extends MiniumException {

	private static final long serialVersionUID = 672390254095024232L;

	public TimeoutException() {
		super();
	}

	public TimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeoutException(String message) {
		super(message);
	}

	public TimeoutException(Throwable cause) {
		super(cause);
	}
}
