package com.web2.safia.auth.dtos;

import com.web2.safia.employee.Employee;

public record SignUpUserResponseDto(String username) {
	public SignUpUserResponseDto(Employee employee) {
		this(employee.getEmail());
	}
}
