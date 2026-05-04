package com.web2.safia.employee.api;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.safia.employee.api.dto.EmployeeResponse;
import com.web2.safia.employee.internal.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/me")
	public ResponseEntity<EmployeeResponse> getMe() {
		return ResponseEntity.ok(employeeService.getById(UUID.randomUUID()));
	}

	@GetMapping
	public ResponseEntity<Page<EmployeeResponse>> getAll(Pageable pageable) {
		return ResponseEntity.ok(employeeService.getAll(pageable));
	}
}
