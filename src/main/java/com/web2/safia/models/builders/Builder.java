package com.web2.safia.models.builders;

public abstract class Builder <T> {
	protected T instance;

	protected Builder() {}

	public abstract Builder<T> builder();

	protected void reset() {
		instance = null;
	}

	public abstract T build();
}
