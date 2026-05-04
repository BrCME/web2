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

import com.web2.safia.work.api.dto.BriefWorkResponse;
import com.web2.safia.work.api.dto.CreateWorkRequest;
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
	public ResponseEntity<Page<BriefWorkResponse>> getMe(Pageable pageable, WebRequest request) {
		return ResponseEntity.ok(workService.getAllByIssuer(pageable, UUID.randomUUID()));
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CreateWorkRequest request) {
		var response = workService.create(request, UUID.randomUUID());
		return ResponseEntity.created(URI.create(response.id().toString())).build();
	}

	@PatchMapping("/{id}/finish")
	public ResponseEntity<Void> finish(@PathVariable UUID id) {
		workService.finish(id, UUID.randomUUID());
		return ResponseEntity.noContent().build();
	}
}
