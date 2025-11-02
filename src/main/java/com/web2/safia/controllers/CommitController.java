package com.web2.safia.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.web2.safia.services.CommitServiceTest;


@Controller
@RequestMapping("/commit")
public class CommitController {
	private final CommitServiceTest commitService;

	public CommitController(CommitServiceTest commitService) {
		this.commitService = commitService;
	}

	@GetMapping("/")
	public String getAll(Pageable pageable, Model model) {
		var commits = commitService.getAll(pageable);

		if (commits.isEmpty()) {
			return "error.html";
		}

		model.addAttribute("commits", commits);

		return "index.html";
	}
}
