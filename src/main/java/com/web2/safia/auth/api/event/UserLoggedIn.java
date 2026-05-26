package com.web2.safia.auth.api.event;

import java.time.LocalDateTime;

import com.web2.safia.shared.vo.UserId;

public record UserLoggedIn(UserId id, String username, LocalDateTime timestamp) {
	public UserLoggedIn(UserId id, String username) {
		this(id, username, LocalDateTime.now());
	}
}
