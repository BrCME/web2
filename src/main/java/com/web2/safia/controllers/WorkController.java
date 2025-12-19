package com.web2.safia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Task;
import com.web2.safia.models.Work;
import com.web2.safia.services.EmployeeService;
import com.web2.safia.services.WorkService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/work")
public class WorkController extends BaseController {
	private final WorkService workService;

	public WorkController(
			WorkService workService,
			EmployeeService employeeService) {

		super(employeeService);
		this.workService = workService;
	}

	@PostMapping("/create")
	public String create(
			Task task,
			@Valid Work work,
			Model model,
			HttpServletRequest request,
			BindingResult result,
			RedirectAttributes redirect) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		workService.create(work, task, creator);

		model.addAttribute("task", work.getTask());
		model.addAttribute("work", new Work());

		return "/task/detail.html";
	}
}
