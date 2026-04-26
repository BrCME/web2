package com.web2.safia.employee.api.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.web2.safia.employee.internal.Employee;

public record BriefEmployeeResponseDto(
		UUID id,
		String name,
		String email,
		String phoneNumber,
		String cpf,
		LocalDate birthDate) {

	public BriefEmployeeResponseDto(Employee employee) {
		this(
				employee.getId(),
				employee.getName(),
				employee.getEmail(),
				employee.getPhoneNumber(),
				employee.getCpf(),
				employee.getBirthDate());
	}

	public BriefEmployeeResponseDto(EmployeeResponseDto responseDto) {
		this(
				responseDto.id(),
				responseDto.name(),
				responseDto.email(),
				responseDto.phoneNumber(),
				responseDto.cpf(),
				responseDto.birthDate());
	}
}
