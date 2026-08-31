package com.web2.safia.auth.internal.mappers;

import org.springframework.stereotype.Component;

import com.web2.safia.auth.api.v1.dtos.UserRoleResponse;
import com.web2.safia.auth.internal.entities.Role;

@Component
public class RoleMapper {
	public UserRoleResponse toUserRoleResponse(Role role) {
		return new UserRoleResponse(role.getType().name());
	}
}
