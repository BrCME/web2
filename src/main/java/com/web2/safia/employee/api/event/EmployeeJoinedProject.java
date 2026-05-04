package com.web2.safia.employee.api.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeJoinedProject(UUID id, String email, String name, UUID projectId, LocalDateTime timestamp) {
	public EmployeeJoinedProject(UUID id, String email, String name, UUID projectId) {
		this(id, email, name, projectId, LocalDateTime.now());
	}
}
