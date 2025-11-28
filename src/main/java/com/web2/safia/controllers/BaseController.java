package com.web2.safia.controllers;

import org.springframework.stereotype.Controller;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.services.EmployeeService;

@Controller
public abstract class BaseController {
	private final EmployeeService employeeService;

	protected BaseController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	protected Employee getCreator(String email) throws DomainException {
		return employeeService.getByEmail(email);
	}
}
