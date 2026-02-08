package com.web2.safia.work;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/works/")
public class WorkController {
	private final WorkService workService;

	public WorkController(WorkService workService) {

		this.workService = workService;
	}

	@PostMapping
	public ResponseEntity<Void> create() {
		workService.create(work, task, creator);
	}
}
