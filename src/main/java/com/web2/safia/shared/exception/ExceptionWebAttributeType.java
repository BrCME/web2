package com.web2.safia.shared.exception;

public enum ExceptionWebAttributeType {
	SESSION, REMOTE, PRINCIPAL, SECURE, LOCALE, CONTEXT, TIMESTAMP;

	public String getNameInLowerCase() {
		return this.toString().toLowerCase();
	}
}
