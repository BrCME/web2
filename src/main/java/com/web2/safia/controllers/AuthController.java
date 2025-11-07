package com.web2.safia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web2.safia.models.Employee;
import com.web2.safia.services.AuthServiceTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private final AuthServiceTest authService;

	public AuthController(
		AuthService authService) {

		this.authService = authService;
	}

	@GetMapping("/sign-up")
	public String signUp() {
		return "/login/signup.html";
	}

	@GetMapping("/sign-in")
	public String signIn() {
		return "/login/signin.html";
	}

	@GetMapping("/sign-out")
	public String signOut() {
		return "redirect:/";
	}

	@PostMapping("/new-employee")
	public String create(
			@Valid Employee employee,
			Model model,
			HttpServletRequest request,
			BindingResult result,
			RedirectAttributes redirect) {

		authService.create(employee);
		return "index.html";
	}
}
