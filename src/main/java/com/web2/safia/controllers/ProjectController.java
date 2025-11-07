package com.web2.safia.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.services.ProjectService;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {
	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping("/")
	public String getAll(Pageable pageable) {
		var projects = projectService.getAll(pageable);

		if (projects.isEmpty()) {
			return "erros.html";
		}
		
		return "projects";
	}
}
