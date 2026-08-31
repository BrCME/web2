package com.web2.safia.auth.api.v1.services;

import org.springframework.data.domain.Page;

import com.web2.safia.auth.api.v1.dtos.SignInUserRequest;
import com.web2.safia.auth.api.v1.dtos.SignUpUserRequest;
import com.web2.safia.auth.api.v1.dtos.SignUpUserResponse;
import com.web2.safia.auth.api.v1.dtos.UserCredentialsResponse;
import com.web2.safia.auth.api.v1.dtos.UserRoleResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
	SignUpUserResponse signUp(SignUpUserRequest requestBody);
	UserCredentialsResponse signIn(SignInUserRequest requestBody);
	void signOut(HttpServletRequest request);
	UserCredentialsResponse activate(SignInUserRequest requestBody);
	Page<UserRoleResponse> getRoles();
}
