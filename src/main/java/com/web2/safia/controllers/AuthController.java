package com.web2.safia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService AuthService) {
		this.authService = AuthService;
	}

	@PostMapping("/login")
	public String login(
			Employee employee) {
		try {
			authService.login(employee);
			return "/login/signup.html";
		} catch (DomainException de) {
			return "/error/404.html";
		} catch (Exception e) {
			return "/error/404.html";
		}
	}

	@PostMapping("/")
	public String createAccount() {
		return "/login/signin.html";
	}

	@GetMapping("sign-up")
	public String signUp(
			Employee employee,
			Model model,
			HttpServletRequest request) {
		model.addAttribute("employee", employee);

		return "/login/signup.html";
	}

	@GetMapping("sign-in")
	public String signIn() {
		return "/login/signin.html";
	}

	@PostMapping("")
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
