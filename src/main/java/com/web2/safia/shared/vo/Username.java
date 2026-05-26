package com.web2.safia.shared.vo;

import java.io.Serializable;
import java.util.Objects;

public record Username(String value) implements Serializable {
	public Username {
		Objects.requireNonNull(value);
	}
}
