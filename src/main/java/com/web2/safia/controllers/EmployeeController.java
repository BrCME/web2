package com.web2.safia.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.services.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("")
	public String getAll(Pageable pageable) {
		var employees = employeeService.getAll(pageable);

		if (employees.isEmpty()) {
			return "error.html";
		}

		return "index.html";
	}
}
