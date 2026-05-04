package com.web2.safia.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record SignInUserRequest(
		@Email(message = "Invalid email format") String username,
		@Size(max = 20, min = 8, message = "Password must have between 8 and 20 characters") String password) {
}
