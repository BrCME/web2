package com.web2.safia.auth.api.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.web2.safia.auth.internal.UserId;

public record UserCreated(
		UserId id,
		String name,
		String email,
		String password,
		String phoneNumber,
		String cpf,
		LocalDate birthDate,
		LocalDateTime timestamp) {

	public UserCreated(
			UserId id,
			String name,
			String email,
			String password,
			String phoneNumber,
			String cpf,
			LocalDate birthDate) {
		this(id, name, email, password, phoneNumber, cpf, birthDate, LocalDateTime.now());
	}
}
