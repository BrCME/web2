package com.web2.safia.team;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apis/teams/")
public class TeamController {
	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@GetMapping
	public ResponseEntity<Void> getAll(Pageable pageable) {
		var teams = teamService.getAll(pageable);
	}

	@GetMapping("me")
	public ResponseEntity<Void> getAllByCreator(Pageable pageable) {
		var creator = getCreator(request.getUserPrincipal().getName());
		var teams = teamService.getAllByCreator(pageable, creator);
	}

	@PostMapping
	public ResponseEntity<Void> create() {
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteById(
			@PathVariable UUID id) {

		teamService.deleteById(id, creator);
	}

	@PutMapping("")
	public ResponseEntity<Void> updateById() {

		teamService.updateById(team, creator);

	}

	@PatchMapping("/add-employee")
	public ResponseEntity<Void> addEmployee() {

		teamService.addEmployee(team, employee.getEmail(), creator);

	}

	@PatchMapping("/remove-employee/{employeeId}")
	public ResponseEntity<Void> removeEmployee(@PathVariable UUID employeeId) {
		teamService.removeEmployee(team, employeeId, creator);

	}
}
