package com.web2.safia.project;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.employee.Employee;
import com.web2.safia.project.dtos.CreateProjectRequestDto;
import com.web2.safia.project.dtos.ProjectResponseDto;
import com.web2.safia.project.dtos.UpdateProjectRequestDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/projects/")
public class ProjectController {
	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping
	public ResponseEntity<Page<ProjectResponseDto>> getAll(Pageable pageable) {
		return ResponseEntity.ok(projectService.getAll(pageable));
	}

	@GetMapping("me")
	public ResponseEntity<Page<ProjectResponseDto>> getAllByIssuer(Pageable pageable) {
		return ResponseEntity.ok(projectService.getAllByIssuer(pageable, new Employee()));
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CreateProjectRequestDto requestDto) {
		var response = projectService.create(requestDto, new Employee());
		return ResponseEntity.created(URI.create(response.id().toString())).build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		projectService.deleteById(id, new Employee());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("{id}")
	public ResponseEntity<ProjectResponseDto> updateById(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateProjectRequestDto requestDto) {

		return ResponseEntity.ok(projectService.updateById(id, requestDto, new Employee()));
	}

	@PatchMapping("{id}/add-employee/{employeeId}")
	public ResponseEntity<ProjectResponseDto> addEmployee(
			@PathVariable UUID id,
			@PathVariable UUID employeeId) {

		return ResponseEntity.ok(projectService.addEmployee(id, employeeId, new Employee()));
	}

	@PatchMapping("{id}/remove-employee/{employeeId}")
	public ResponseEntity<ProjectResponseDto> removeEmployee(
			@PathVariable UUID id,
			@PathVariable UUID employeeId) {

		return ResponseEntity.ok(projectService.removeEmployee(id, employeeId, new Employee()));
	}
}
