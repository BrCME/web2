package com.web2.safia.shared.vo;

import java.io.Serializable;

import com.web2.safia.shared.util.CustomIdUtils;


public record TeamId(String value) implements Serializable {
	public TeamId() {
		this(CustomIdUtils.createId("TeamId"));
	}
}
