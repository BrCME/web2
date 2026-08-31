package com.web2.safia.employee.api.event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public record EmployeeExitedProject(UUID id, String email, String name, UUID projectId, LocalDateTime timestamp) {
	public EmployeeExitedProject(UUID id, String email, String name, UUID projectId) {
		this(id, email, name, projectId, LocalDateTime.now(ZoneOffset.UTC));
	}
}
