package com.web2.safia.task.api.event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public record TaskFinished(UUID id, LocalDateTime timestamp) {
	public TaskFinished(UUID id) {
		this(id, LocalDateTime.now(ZoneOffset.UTC));
	}
}
