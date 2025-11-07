package com.web2.safia.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.web2.safia.services.TeamServiceTest;

@Controller
@RequestMapping("/team")
public class TeamController {
	private final TeamServiceTest teamService;

	public TeamController(TeamServiceTest teamService) {
		this.teamService = teamService;
	}

	@GetMapping("")
	public String getAll(Pageable pageable) {
		var teams = teamService.getAll(pageable);

		if (teams.isEmpty()) {
			return "error.html";
		}

		return "/teams";
	}

}
