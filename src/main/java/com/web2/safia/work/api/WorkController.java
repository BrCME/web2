package com.web2.safia.work.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.safia.employee.internal.Employee;
import com.web2.safia.work.internal.WorkService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/works/")
public class WorkController {
	private final WorkService workService;

	public WorkController(WorkService workService) {
		this.workService = workService;
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CreateWorkRequestDto requestDto) {
		var response = workService.create(requestDto, new Employee());
		return ResponseEntity.created(URI.create(response.id().toString())).build();
	}
}
