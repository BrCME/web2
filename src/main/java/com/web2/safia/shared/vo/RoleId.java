package com.web2.safia.shared.vo;

import java.io.Serializable;

import com.web2.safia.shared.util.CustomIdUtils;

public record RoleId(String value) implements Serializable {
	public RoleId() {
		this(CustomIdUtils.createId("RoleId"));
	}
}
