package com.web2.safia.auth.api.dto;

import java.util.UUID;

import com.web2.safia.shared.entity.Employee;

public record SignUpUserResponseDto(UUID id, String username) {
	public SignUpUserResponseDto(Employee employee) {
		this(employee.getId(), employee.getEmail());
	}
}
