package com.web2.safia.auth;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.auth.dtos.SignInUserRequestDto;
import com.web2.safia.auth.dtos.SignUpUserRequestDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/auths/")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("sign-up")
	public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpUserRequestDto requestDto) {
		logger.info("User trying to sign up: ", requestDto);
		var response = authService.signUp(requestDto);
		return ResponseEntity.created(URI.create(response.username())).build();
	}

	@PostMapping("sign-in")
	public ResponseEntity<Void> signIn(@Valid @RequestBody SignInUserRequestDto requestDto) {
		logger.info("User trying to sign in: ", requestDto);
		authService.signIn(requestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("sign-out")
	public ResponseEntity<Void> signOut() {
		return ResponseEntity.noContent().build();
	}
}
