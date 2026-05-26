package com.web2.safia.shared.vo;

import java.io.Serializable;

import com.web2.safia.shared.util.CustomIdUtils;

public record ProjectId(String value) implements Serializable {
	public ProjectId() {
		this(CustomIdUtils.createId("ProjectId"));
	}
}
