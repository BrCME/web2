package com.web2.safia.employee.dtos;

import java.time.LocalDate;

import com.web2.safia.employee.Employee;

public record BriefEmployeeResponseDto(
		String name,
		String email,
		String phoneNumber,
		String cpf,
		LocalDate birthDate) {

	public BriefEmployeeResponseDto(Employee employee) {
		this(
				employee.getName(),
				employee.getEmail(),
				employee.getPhoneNumber(),
				employee.getCpf(),
				employee.getBirthDate());
	}

	public BriefEmployeeResponseDto(EmployeeResponseDto employee) {
		this(
				employee.name(),
				employee.email(),
				employee.phoneNumber(),
				employee.cpf(),
				employee.birthDate());
	}
}
