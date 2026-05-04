package com.web2.safia.auth.api.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreated(UUID id, String username, LocalDateTime timestamp) {
	public UserCreated(UUID id, String username) {
		this(id, username, LocalDateTime.now());
	}
}
