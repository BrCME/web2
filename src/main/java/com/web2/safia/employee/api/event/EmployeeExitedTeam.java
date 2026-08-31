package com.web2.safia.employee.api.event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public record EmployeeExitedTeam(UUID id, String email, String name, UUID teamId, LocalDateTime timestamp) {
	public EmployeeExitedTeam(UUID id, String email, String name, UUID teamId) {
		this(id, email, name, teamId, LocalDateTime.now(ZoneOffset.UTC));
	}
}
