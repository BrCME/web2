package com.web2.safia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web2.safia.models.Employee;
import com.web2.safia.services.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
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

	@PostMapping("")
	public String create(
			@Valid Employee employee,
			Model model,
			HttpServletRequest request,
			BindingResult result,
			RedirectAttributes redirect) {

		logger.info("Empregado: {}", employee);
		logger.info("Modelo: {}", model);
		logger.info("Requisição: {}", request);
		logger.info("Resultado: {}", result);
		logger.info("Redireção: {}", redirect);

		employeeService.create(employee);

		return "index.html";
	}

}
