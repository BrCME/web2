package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.repositories.adapters.JpaEmployeeRepository;

public class EmployeeServiceTest {
	private EmployeeService underTest;
	
	@Mock
	private JpaEmployeeRepository employeeRepository;
	
	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new EmployeeService(employeeRepository);
	}

	@Test
	void givenValidPageable_whenGetAll_thenReturnEmployeePage() {
	}

	@Test
	void givenInvalidId_whenGetById_thenThrowException() {
	}

	@Test
	void givenValidId_whenGetById_thenReturnEmployee() {
	}

	@Test
	void givenInvalidEmail_whenGetByEmail_thenThrowException() {
	}

	@Test
	void givenValidEmail_whenGetByEmail_thenReturnEmployee() {
	}

	@Test
	void givenValidCreator_whenGetAllByCreator_thenReturnEmployees() {
	}
}
