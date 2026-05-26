package com.web2.safia.auth.api;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.web2.safia.auth.api.dto.SignInUserRequest;
import com.web2.safia.auth.api.dto.SignUpUserRequest;
import com.web2.safia.auth.internal.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/sign-up")
	public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpUserRequest request) {
		var response = authService.signUp(request);
		return ResponseEntity.created(URI.create(response.username())).build();
	}

	@PostMapping("/sign-in")
	public ResponseEntity<Void> signIn(@Valid @RequestBody SignInUserRequest request) {
		authService.signIn(request);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/sign-out")
	public ResponseEntity<Void> signOut() {
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/roles")
	public ResponseEntity<Void> getRoles() {
		return ResponseEntity.noContent().build();
	}
}
