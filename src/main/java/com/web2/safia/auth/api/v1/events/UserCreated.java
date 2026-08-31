package com.web2.safia.auth.api.v1.events;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.web2.safia.shared.vo.UserId;

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
		this(id, name, email, password, phoneNumber, cpf, birthDate, LocalDateTime.now(ZoneOffset.UTC));
	}
}
