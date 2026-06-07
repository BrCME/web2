package com.web2.safia.shared.exception;

public enum WebExceptionAttributeType {
	SESSION, REMOTE, PRINCIPAL, SECURE, LOCALE, CONTEXT, TIMESTAMP;

	public String getNameInLowerCase() {
		return this.toString().toLowerCase();
	}
}
