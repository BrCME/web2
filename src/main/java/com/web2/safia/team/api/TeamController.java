package com.web2.safia.team.api;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.safia.team.api.dto.BriefTeamResponse;
import com.web2.safia.team.api.dto.CreateTeamRequest;
import com.web2.safia.team.api.dto.UpdateTeamRequest;
import com.web2.safia.team.internal.TeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@GetMapping
	public ResponseEntity<Page<BriefTeamResponse>> getAll(Pageable pageable) {
		return ResponseEntity.ok(teamService.getAll(pageable));
	}

	@GetMapping("/me")
	public ResponseEntity<Page<BriefTeamResponse>> getAllByIssuer(Pageable pageable) {
		return ResponseEntity.ok(teamService.getAllByIssuer(pageable, UUID.randomUUID()));
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CreateTeamRequest request) {
		var response = teamService.create(request, UUID.randomUUID());
		return ResponseEntity.created(URI.create(response.id().toString())).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		teamService.deleteById(id, UUID.randomUUID());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<BriefTeamResponse> updateById(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateTeamRequest request) {

		return ResponseEntity.ok(teamService.updateById(id, request, UUID.randomUUID()));
	}

	@PatchMapping("/{id}/add-employee/{employeeId}")
	public ResponseEntity<BriefTeamResponse> addEmployee(
			@PathVariable UUID id,
			@PathVariable UUID employeeId) {

		return ResponseEntity.ok(teamService.addEmployee(id, employeeId, UUID.randomUUID()));
	}

	@PatchMapping("/{id}/remove-employee/{employeeId}")
	public ResponseEntity<BriefTeamResponse> removeEmployee(
			@PathVariable UUID id,
			@PathVariable UUID employeeId) {

		return ResponseEntity.ok(teamService.removeEmployee(id, employeeId, UUID.randomUUID()));
	}
}
