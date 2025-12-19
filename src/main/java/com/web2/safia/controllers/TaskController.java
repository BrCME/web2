package com.web2.safia.controllers;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Project;
import com.web2.safia.models.Task;
import com.web2.safia.services.EmployeeService;
import com.web2.safia.services.TaskService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {
	private final TaskService taskService;

	public TaskController(
			EmployeeService employeeService,
			TaskService taskService) {

		super(employeeService);
		this.taskService = taskService;
	}

	@GetMapping("/all")
	public String getAll(
			Pageable pageable,
			Model model) {

		var tasks = taskService.getAll(pageable);
		model.addAttribute("tasks", tasks);


		return "/task/index.html";
	}

	@GetMapping("/me")
	public String getAllByCreator(
			Pageable pageable,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		var tasks = taskService.getAllByCreator(pageable, creator);
		model.addAttribute("tasks", tasks);
		model.addAttribute("total", tasks.getTotalElements());

		return "/task/me.html";
	}

	@GetMapping("/detail/{id}")
	public String getDetail(
			@PathVariable("id") UUID id,
			Model model,
			HttpServletRequest request) throws DomainException {

		var task = taskService.getById(id);
		model.addAttribute("task", task);

		return "/task/detail.html";
	}

	@GetMapping("/create")
	public String getCreatePage(
			Task task,
			Model model,
			HttpServletRequest request) {

		model.addAttribute("task", task);

		return "/task/create.html";
	}

	@PostMapping("/create")
	public String create(
			Project project,
			@Valid Task task,
			Model model,
			HttpServletRequest request,
			BindingResult result,
			RedirectAttributes redirect) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		taskService.create(project, task, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/delete/{id}")
	public String deleteById(
			@PathVariable("id") UUID id,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		taskService.deleteById(id, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/update")
	public String updateById(
			Task task,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		taskService.updateById(task, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}
}
