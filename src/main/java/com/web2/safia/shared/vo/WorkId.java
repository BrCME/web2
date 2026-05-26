package com.web2.safia.shared.vo;

import java.io.Serializable;

import com.web2.safia.shared.util.CustomIdUtils;

public record WorkId(String value) implements Serializable {
	public WorkId() {
		this(CustomIdUtils.createId("WorkId"));
	}
}
