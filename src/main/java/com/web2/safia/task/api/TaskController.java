package com.web2.safia.task.api;

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

import com.web2.safia.task.api.dto.BriefTaskResponseDto;
import com.web2.safia.task.api.dto.CreateTaskRequestDto;
import com.web2.safia.task.api.dto.TaskResponseDto;
import com.web2.safia.task.api.dto.UpdateTaskRequestDto;
import com.web2.safia.task.internal.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping
	public ResponseEntity<Page<TaskResponseDto>> getAll(Pageable pageable) {
		return ResponseEntity.ok(taskService.getAll(pageable));
	}

	@GetMapping("/me")
	public ResponseEntity<Page<TaskResponseDto>> getAllByIssuer(Pageable pageable) {
		return ResponseEntity.ok(taskService.getAllByIssuer(pageable, UUID.randomUUID()));
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CreateTaskRequestDto requestDto) {
		var response = taskService.create(requestDto, UUID.randomUUID());
		return ResponseEntity.created(URI.create(response.id().toString())).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<BriefTaskResponseDto> updateById(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateTaskRequestDto requestDto) {

		return ResponseEntity.ok(taskService.updateById(id, requestDto, UUID.randomUUID()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		taskService.deleteById(id, UUID.randomUUID());
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/promote")
	public ResponseEntity<BriefTaskResponseDto> promoteById(@PathVariable UUID id) {
		return ResponseEntity.ok(taskService.promote(id, UUID.randomUUID()));
	}

	@PatchMapping("/{id}/demote")
	public ResponseEntity<BriefTaskResponseDto> demoteById(@PathVariable UUID id) {
		return ResponseEntity.ok(taskService.demote(id, UUID.randomUUID()));
	}
}
