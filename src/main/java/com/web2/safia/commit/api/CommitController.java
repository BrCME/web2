package com.web2.safia.commit.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.safia.commit.api.dto.CommitResponse;
import com.web2.safia.commit.internal.CommitService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Commit")
@RestController
@RequestMapping("/api/commits")
public class CommitController {
	private final CommitService commitService;

	public CommitController(CommitService commitService) {
		this.commitService = commitService;
	}

	@GetMapping
	public ResponseEntity<Page<CommitResponse>> getAll(Pageable pageable) {
		return ResponseEntity.ok(commitService.getAll(pageable));
	}
}
