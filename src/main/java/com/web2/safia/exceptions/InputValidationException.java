package com.web2.safia.exceptions;

public class InputValidationException extends RuntimeException {
	public InputValidationException() {
		super();
	}

	public InputValidationException(String message) {
		super(message);
	}

	public InputValidationException(Throwable cause) {
		super(cause);
	}

	public InputValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	protected InputValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
