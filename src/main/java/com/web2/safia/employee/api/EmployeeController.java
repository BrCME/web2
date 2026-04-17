package com.web2.safia.employee.api;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.safia.employee.internal.ImplEmployeeService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	private final ImplEmployeeService employeeService;

	public EmployeeController(ImplEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/me")
	@ApiResponse
	public ResponseEntity<EmployeeResponseDto> getMe() {
		return ResponseEntity.ok(employeeService.getById(UUID.randomUUID()));
	}

	@GetMapping
	public ResponseEntity<Page<EmployeeResponseDto>> getAll(Pageable pageable) {
		return ResponseEntity.ok(employeeService.getAll(pageable));
	}
}
