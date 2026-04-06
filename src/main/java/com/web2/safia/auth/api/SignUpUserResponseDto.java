package com.web2.safia.auth.api;

import com.web2.safia.employee.internal.Employee;

public record SignUpUserResponseDto(String username) {
	public SignUpUserResponseDto(Employee employee) {
		this(employee.getEmail());
	}
}
