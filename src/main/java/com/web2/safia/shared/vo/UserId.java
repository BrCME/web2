package com.web2.safia.shared.vo;

import java.io.Serializable;

import com.web2.safia.shared.util.CustomIdUtils;

public record UserId(String value) implements Serializable {
	public UserId() {
		this(CustomIdUtils.createId("UserId"));
	}
}
