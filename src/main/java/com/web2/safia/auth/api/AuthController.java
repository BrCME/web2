package com.web2.safia.auth.api;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.safia.auth.api.dto.SignInUserRequest;
import com.web2.safia.auth.api.dto.SignUpUserRequest;
import com.web2.safia.auth.api.dto.UserRoleResponse;
import com.web2.safia.auth.internal.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Auth")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/sign-up")
	public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpUserRequest requestBody) {
		var response = authService.signUp(requestBody);
		return ResponseEntity.created(URI.create(response.username())).build();
	}

	@PostMapping("/sign-in")
	public ResponseEntity<Void> signIn(@Valid @RequestBody SignInUserRequest requestBody) {
		authService.signIn(requestBody);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/sign-out")
	public ResponseEntity<Void> signOut() {
		authService.signOut();
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/roles")
	public ResponseEntity<Page<UserRoleResponse>> getRoles() {
		return ResponseEntity.ok(authService.getRoles());
	}
}
