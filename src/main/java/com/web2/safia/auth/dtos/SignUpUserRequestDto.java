package com.web2.safia.auth.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record SignUpUserRequestDto(
		@NotBlank(message = "Name cannot be blank") String name,
		@Email(message = "Invalid email") String email,
		@Size(max = 20, min = 8, message = "Password must have between 8 and 20 characters") String password,
		@Size(max = 11, min = 11, message = "Phone number must have 11 characters") String phoneNumber,
		@Size(max = 11, min = 11, message = "CPF must have 11 characters") String cpf,
		@Past(message = "Birth date must be in past") LocalDate birthDate) { }
