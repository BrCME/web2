package com.web2.safia.commit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.commit.dtos.CommitResponseDto;

@Controller
@RequestMapping("/api/commits/")
public class CommitController {
	private final CommitService commitService;

	public CommitController(CommitService commitService) {
		this.commitService = commitService;
	}

	@GetMapping
	public ResponseEntity<Page<CommitResponseDto>> getAll(Pageable pageable) {
		return ResponseEntity.ok(commitService.getAll(pageable));
	}
}
