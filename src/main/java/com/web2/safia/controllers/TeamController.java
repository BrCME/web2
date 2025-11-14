package com.web2.safia.controllers;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.models.Team;
import com.web2.safia.services.EmployeeService;
import com.web2.safia.services.TeamService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/team")
public class TeamController {
	private final EmployeeService employeeService;
	private final TeamService teamService;

	public TeamController(
			TeamService teamService,
			EmployeeService employeeService) {

		this.teamService = teamService;
		this.employeeService = employeeService;
	}

	@GetMapping("/all")
	public String getAll(
			Pageable pageable,
			Model model) {

		var teams = teamService.getAll(pageable);
		model.addAttribute("teams", teams);

		return "/team/index.html";
	}

	@GetMapping("/me")
	public String getAllByCreator(
			Pageable pageable,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		var teams = teamService.getAllByCreator(pageable, creator);
		model.addAttribute("teams", teams);
		model.addAttribute("total", teams.getTotalElements());

		return "/team/me.html";
	}

	@GetMapping("/detail/{id}")
	public String getMethodName(@PathVariable("id") String id) {
		return "/index.html";
	}
	

	@GetMapping("/create")
	public String getCreatePage(
			Team team,
			Model model,
			HttpServletRequest request) {

		model.addAttribute("team", team);

		return "/team/create.html";
	}

	@PostMapping("/create")
	public String create(
			@Valid Team team,
			Model model,
			HttpServletRequest request,
			BindingResult result,
			RedirectAttributes redirect) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		teamService.create(team, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/delete-team/{teamId}")
	public String deleteById(
			@PathVariable("teamId") UUID teamId,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		teamService.deleteById(teamId, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/update")
	public String updateById(
			Team team,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		teamService.updateById(team, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/add-employee/{employeeId}")
	public String addEmployee(
			@PathVariable("employeeId") UUID employeeId,
			Team team,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		teamService.addEmployee(team, employeeId, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	@PostMapping("/remove-employee/{employeeId}")
	public String removeEmployee(
			@PathVariable("employeeId") UUID employeeId,
			Team team,
			Model model,
			HttpServletRequest request) throws DomainException {

		var creator = getCreator(request.getUserPrincipal().getName());
		teamService.removeEmployee(team, employeeId, creator);

		return getAllByCreator(Pageable.ofSize(20), model, request);
	}

	private Employee getCreator(String email) throws DomainException {
		return employeeService.getByEmail(email);
	}
}
