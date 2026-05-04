package com.web2.safia.project.api;

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

import com.web2.safia.project.api.dto.CreateProjectRequest;
import com.web2.safia.project.api.dto.ProjectResponse;
import com.web2.safia.project.api.dto.UpdateProjectRequest;
import com.web2.safia.project.internal.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping
	public ResponseEntity<Page<ProjectResponse>> getAll(Pageable pageable) {
		return ResponseEntity.ok(projectService.getAll(pageable));
	}

	@GetMapping("/me")
	public ResponseEntity<Page<ProjectResponse>> getAllByIssuer(Pageable pageable) {
		return ResponseEntity.ok(projectService.getAllByIssuer(pageable, UUID.randomUUID()));
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CreateProjectRequest request) {
		var response = projectService.create(request, UUID.randomUUID());
		return ResponseEntity.created(URI.create(response.id().toString())).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		projectService.deleteById(id, UUID.randomUUID());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProjectResponse> updateById(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateProjectRequest request) {

		return ResponseEntity.ok(projectService.updateById(id, request, UUID.randomUUID()));
	}

	@PatchMapping("/{id}/add-employee/{employeeId}")
	public ResponseEntity<ProjectResponse> addEmployee(
			@PathVariable UUID id,
			@PathVariable UUID employeeId) {

		return ResponseEntity.ok(projectService.addEmployee(id, employeeId, UUID.randomUUID()));
	}

	@PatchMapping("/{id}/remove-employee/{employeeId}")
	public ResponseEntity<ProjectResponse> removeEmployee(
			@PathVariable UUID id,
			@PathVariable UUID employeeId) {

		return ResponseEntity.ok(projectService.removeEmployee(id, employeeId, UUID.randomUUID()));
	}
}
