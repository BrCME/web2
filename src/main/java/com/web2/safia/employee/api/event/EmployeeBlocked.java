package com.web2.safia.employee.api.event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public record EmployeeBlocked(UUID id, String email, String name, LocalDateTime timestamp) {
	public EmployeeBlocked(UUID id, String email, String name) {
		this(id, email, name, LocalDateTime.now(ZoneOffset.UTC));
	}
}
