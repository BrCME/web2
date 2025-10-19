package com.web2.safia.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.web2.safia.services.CommitService;


@Controller
@RequestMapping("/commit")
public class CommitController {
	private final CommitService commitService;

	public CommitController(CommitService commitService) {
		this.commitService = commitService;
	}

	@GetMapping("/")
	public String getAll(Pageable pageable) {
		var commits = commitService.getAll(pageable);

		if (commits.isEmpty()) {
			return "error.html";
		}

		return "index.html";
	}
}
