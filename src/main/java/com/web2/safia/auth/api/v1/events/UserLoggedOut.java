package com.web2.safia.auth.api.v1.events;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.web2.safia.shared.vo.UserId;

public record UserLoggedOut(UserId id, String username, LocalDateTime timestamp) {
	public UserLoggedOut(UserId id, String username) {
		this(id, username, LocalDateTime.now(ZoneOffset.UTC));
	}
}
