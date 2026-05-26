package com.web2.safia.auth.api.dto;

import com.web2.safia.auth.internal.Role;

public record UserRoleResponse(String name) {
	public UserRoleResponse(Role role) {
		this(role.getType().name());
	}
}
