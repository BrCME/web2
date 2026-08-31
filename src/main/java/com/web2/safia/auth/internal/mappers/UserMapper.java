package com.web2.safia.auth.internal.mappers;

import org.springframework.stereotype.Component;

import com.web2.safia.auth.api.v1.dtos.SignUpUserRequest;
import com.web2.safia.auth.api.v1.dtos.SignUpUserResponse;
import com.web2.safia.auth.internal.entities.User;

@Component
public class UserMapper {
	public User toEntity(SignUpUserRequest requestBody, String encodedPassword) {
		var user = new User();
		user.setUsername(requestBody.email());
		user.setPassword(encodedPassword);
		return user;
	}

	public SignUpUserResponse toSignUpUserResponse(User user) {
		return new SignUpUserResponse(user.getId(), user.getUsername());
	}
}
