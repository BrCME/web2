package com.web2.safia.team;

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

import com.web2.safia.employee.Employee;
import com.web2.safia.team.dtos.BriefTeamResponseDto;
import com.web2.safia.team.dtos.CreateTeamRequestDto;
import com.web2.safia.team.dtos.UpdateTeamRequestDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teams/")
public class TeamController {
	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@GetMapping
	public ResponseEntity<Page<BriefTeamResponseDto>> getAll(Pageable pageable) {
		return ResponseEntity.ok(teamService.getAll(pageable));
	}

	@GetMapping("me")
	public ResponseEntity<Page<BriefTeamResponseDto>> getAllByIssuer(Pageable pageable) {
		return ResponseEntity.ok(teamService.getAllByIssuer(pageable, new Employee()));
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CreateTeamRequestDto requestDto) {
		var response = teamService.create(requestDto, new Employee());
		return ResponseEntity.created(URI.create(response.id().toString())).build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		teamService.deleteById(id, new Employee());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("{id}")
	public ResponseEntity<BriefTeamResponseDto> updateById(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateTeamRequestDto requestDto) {

		return ResponseEntity.ok(teamService.updateById(id, requestDto, new Employee()));
	}

	@PatchMapping("{id}/add-employee/{employeeId}")
	public ResponseEntity<BriefTeamResponseDto> addEmployee(
			@PathVariable UUID id,
			@PathVariable UUID employeeId) {

		return ResponseEntity.ok(teamService.addEmployee(id, employeeId, new Employee()));
	}

	@PatchMapping("{id}/remove-employee/{employeeId}")
	public ResponseEntity<BriefTeamResponseDto> removeEmployee(
		@PathVariable UUID id,
		@PathVariable UUID employeeId) {
		
		return ResponseEntity.ok(teamService.removeEmployee(id, employeeId, new Employee()));
	}
}
