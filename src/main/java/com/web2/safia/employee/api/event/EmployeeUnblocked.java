package com.web2.safia.employee.api.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeUnblocked(UUID id, String email, String name, LocalDateTime timestamp) {
	public EmployeeUnblocked(UUID id, String email, String name) {
		this(id, email, name, LocalDateTime.now());
	}
}
