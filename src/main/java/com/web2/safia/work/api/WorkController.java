package com.web2.safia.work.api;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.web2.safia.work.api.dto.BriefWorkResponseDto;
import com.web2.safia.work.api.dto.CreateWorkRequestDto;
import com.web2.safia.work.internal.WorkService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/works")
public class WorkController {
	private final WorkService workService;

	public WorkController(WorkService workService) {
		this.workService = workService;
	}

	@GetMapping("/me")
	public ResponseEntity<Page<BriefWorkResponseDto>> getMe(Pageable pageable, WebRequest request) {
		return ResponseEntity.ok(workService.getAllByIssuer(pageable, UUID.randomUUID()));
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CreateWorkRequestDto requestDto) {
		var response = workService.create(requestDto, UUID.randomUUID());
		return ResponseEntity.created(URI.create(response.id().toString())).build();
	}

	@PatchMapping("/{id}/finish")
	public ResponseEntity<Void> finish(@PathVariable UUID id) {
		workService.finish(id);
		return ResponseEntity.noContent().build();
	}
}
