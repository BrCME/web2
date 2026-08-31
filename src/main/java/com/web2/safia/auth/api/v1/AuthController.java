package com.web2.safia.auth.api.v1;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.safia.auth.api.v1.dtos.SignInUserRequest;
import com.web2.safia.auth.api.v1.dtos.SignUpUserRequest;
import com.web2.safia.auth.api.v1.dtos.UserCredentialsResponse;
import com.web2.safia.auth.api.v1.dtos.UserRoleResponse;
import com.web2.safia.auth.api.v1.services.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Auth")
@RestController
@RequestMapping("/api/v1/auth")
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

	@PostMapping("/activate")
	public ResponseEntity<UserCredentialsResponse> activate(@Valid @RequestBody SignInUserRequest requestBody) {
		return ResponseEntity.ok(authService.activate(requestBody));
	}

	@PostMapping("/sign-in")
	public ResponseEntity<UserCredentialsResponse> signIn(@Valid @RequestBody SignInUserRequest requestBody) {
		return ResponseEntity.ok(authService.signIn(requestBody));
	}

	@PostMapping("/sign-out")
	public ResponseEntity<Void> signOut(HttpServletRequest request) {
		authService.signOut(request);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/roles")
	public ResponseEntity<Page<UserRoleResponse>> getRoles() {
		return ResponseEntity.ok(authService.getRoles());
	}
}
