package com.web2.safia.shared.vo;

import java.io.Serializable;

import com.web2.safia.shared.util.CustomIdUtils;

public record EmployeeId(String value) implements Serializable {
	public EmployeeId() {
		this(CustomIdUtils.createId("EmployeeId"));
	}
}
