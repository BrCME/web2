package com.web2.safia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.services.TaskService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/task")
public class TaskController {
	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping("/")
	public String task() {
		return "/task/index.html";
	}

	@GetMapping("/working")
	public String working() {
		return "/task/working.html";
	}

	@GetMapping("/card")
	public String card() {
		return "/task/card.html";
	}
	
}
