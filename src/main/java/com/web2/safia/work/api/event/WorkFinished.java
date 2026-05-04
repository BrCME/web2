package com.web2.safia.work.api.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkFinished(UUID id, UUID employeeId, LocalDateTime timestamp) {
	public WorkFinished(UUID id, UUID employeeId) {
		this(id, employeeId, LocalDateTime.now());
	}
}
