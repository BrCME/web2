package com.web2.safia.controllers;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Project;
import com.web2.safia.models.Team;
import com.web2.safia.services.EmployeeService;
import com.web2.safia.services.ProjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
	private final ProjectService projectService;

	public ProjectController(
			EmployeeService employeeService,
			ProjectService projectService) {

		super(employeeService);
		this.projectService = projectService;
	}

	@GetMapping("/all")
	public String getAll(
			Pageable pageable,
			Model model) {

		var projects = projectService.getAll(pageable);
		model.addAttribute("projects", projects);

		return "/project/index.html";
	}

	@GetMapping("/me")
	public String getAllByCreator(
		Pageable pageable,
		Model model,
		HttpServletRequest request) throws DomainException {
		
			var creator = getCreator(request.getUserPrincipal().getName());
			var projects = projectService.getAllByCreator(pageable, creator);
			model.addAttribute("projects", projects);
			model.addAttribute("total", projects);

			return "/project/me.html";
	}

	@GetMapping("/detail/{id}")
	public String getDetail(
			@PathVariable("id") UUID id,
			Model model,
			HttpServletRequest request) throws DomainException {

		var project = projectService.getById(id);
		model.addAttribute("project", project);

		return "/project/detail.html";
	}

	@GetMapping("/create")
	public String getCreatePage(
			Project project,
			Model model,
			HttpServletRequest request) {

		model.addAttribute("project", project);
		model.addAttribute("newProject", new Project());

		return "/team/create.html";
	}

	@PostMapping("/create")
	public String create(
			Team team,
			@Valid Project project,
			Model model,
			HttpServletRequest request,
			BindingResult result,
			RedirectAttributes redirect) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		projectService.create(team, project, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/delete/{id}")
	public String deleteById(
			@PathVariable("id") UUID id,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		projectService.deleteById(id, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/update")
	public String updateById(
			Project project,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		projectService.updateById(project, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/add-employee/{employeeId}")
	public String addEmployee(
			@PathVariable("employeeId") UUID employeeId,
			Project project,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		projectService.addEmployee(project, employeeId, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/remove-employee/{employeeId}")
	public String removeEmployee(
			@PathVariable("employeeId") UUID employeeId,
			Project project,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		projectService.removeEmployee(project, employeeId, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}
}
