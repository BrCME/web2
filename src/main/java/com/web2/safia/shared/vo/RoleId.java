package com.web2.safia.shared.vo;

import java.io.Serializable;

import com.web2.safia.shared.util.CustomIdUtils;

import jakarta.persistence.Embeddable;

@Embeddable
public record RoleId(String id) implements Serializable {
	public RoleId() {
		this(CustomIdUtils.createId("RoleId"));
	}
}
