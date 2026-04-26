package com.web2.safia.auth.api;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.safia.auth.api.dto.SignInUserRequestDto;
import com.web2.safia.auth.api.dto.SignUpUserRequestDto;
import com.web2.safia.auth.internal.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/sign-up")
	public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpUserRequestDto requestDto) {
		logger.info("User trying to sign up: {}", requestDto);
		var response = authService.signUp(requestDto);
		return ResponseEntity.created(URI.create(response.username())).build();
	}

	@PostMapping("/sign-in")
	public ResponseEntity<Void> signIn(@Valid @RequestBody SignInUserRequestDto requestDto) {
		logger.info("User trying to sign in: {}", requestDto);
		authService.signIn(requestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/sign-out")
	public ResponseEntity<Void> signOut() {
		return ResponseEntity.noContent().build();
	}
}
