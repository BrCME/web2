package com.web2.safia.employee.api;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web2.safia.employee.internal.Employee;

public interface EmployeeService {
	Page<EmployeeResponseDto> getAll(Pageable pageable);
	EmployeeResponseDto getById(UUID id);
	EmployeeResponseDto getByEmail(String email);	
	Set<Employee> getAllByCreator(Employee creator);
}
