package com.web2.safia.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.services.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/me")
	public String getMe(
			Model model,
			HttpServletRequest request) throws DomainException {

		var employee = getCreator(request.getUserPrincipal().getName());

		model.addAttribute("roles", employee.getAllRoles());
		model.addAttribute("projects", employeeService.getAllProjects(employee));
		model.addAttribute("teams", employeeService.getAllTeams(employee));
		model.addAttribute("works", employeeService.getAllWorks(employee));
		model.addAttribute("tasks", employeeService.getAllTasks(employee));

		return "/employee/me.html";
	}

	@GetMapping("")
	public String getAll(Pageable pageable) {
		var employees = employeeService.getAll(pageable);

		if (employees.isEmpty()) {
			return "error.html";
		}

		return "index.html";
	}

	private Employee getCreator(String email) throws DomainException {
		return employeeService.getByEmail(email);
	}
}
