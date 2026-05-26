package com.web2.safia.shared.vo;

import java.io.Serializable;

import com.web2.safia.shared.util.CustomIdUtils;

public record TaskId(String value) implements Serializable {
	public TaskId() {
		this(CustomIdUtils.createId("TaskId"));
	}
}
