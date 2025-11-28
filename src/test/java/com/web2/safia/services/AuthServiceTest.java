package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaRoleRepository;

public class AuthServiceTest {
	private AuthService underTest;

	@Mock
	private CommitEventPublisher commitEventPublisher;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JpaEmployeeRepository employeeRepository;

	@Mock
	private JpaRoleRepository roleRepository;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new AuthService(commitEventPublisher, passwordEncoder, employeeRepository, roleRepository);
	}

	@Test
	void givenInvalidEmail_whenLogin_thenThrowException() {
	}

	@Test
	void givenInvalidPassword_whenLogin_thenThrowException() {
	}

	@Test
	void givenValidEmployee_whenLogin_thenReturEmployee() {
	}

	@Test
	void givenInvalidEmployee_whenCreate_thenThrowException() {
	}

	@Test
	void givenValidEmployee_whenCreate_thenReturnVoid() {
	}
}
