package com.web2.safia.auth.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record SignInUserRequestDto(
		@Email(message = "Invalid email") String username,
		@Size(max = 20, min = 8, message = "Password must have between 8 and 20 characters") String password) {
}
