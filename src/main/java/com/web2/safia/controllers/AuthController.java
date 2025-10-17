package com.web2.safia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.dtos.AuthLoginDto;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.services.AuthService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService AuthService) {
		this.authService = AuthService;
	}

	@PostMapping("/login")
	public String login(@RequestBody AuthLoginDto authLoginDto) {
		try {
			authService.login(authLoginDto);
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
}
