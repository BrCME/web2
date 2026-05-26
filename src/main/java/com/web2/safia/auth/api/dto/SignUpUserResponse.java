package com.web2.safia.auth.api.dto;

import com.web2.safia.auth.internal.User;
import com.web2.safia.auth.internal.UserId;

public record SignUpUserResponse(UserId id, String username) {
	public SignUpUserResponse(User user) {
		this(user.getId(), user.getUsername());
	}
}
