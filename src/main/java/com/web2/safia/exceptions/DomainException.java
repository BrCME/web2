package com.web2.safia.exceptions;

public class DomainException extends RuntimeException {
	public DomainException() {
		super();
	}

	public DomainException(String message) {
		super(message);
	}

	public DomainException(Throwable cause) {
		super(cause);
	}

	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}

	protected DomainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
