package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaProjectRepository;
import com.web2.safia.repositories.adapters.JpaTaskRepository;
import com.web2.safia.repositories.adapters.JpaTeamRepository;
import com.web2.safia.repositories.adapters.JpaWorkRepository;

public class EmployeeServiceTest {
	private EmployeeService underTest;
	
	@Mock
	private JpaEmployeeRepository employeeRepository;

	@Mock
	private JpaProjectRepository projectRepository;

	@Mock
	private JpaTeamRepository teamRepository;

	@Mock
	private JpaWorkRepository workRepository;

	@Mock
	private JpaTaskRepository taskRepository;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new EmployeeService(employeeRepository, projectRepository, teamRepository, workRepository, taskRepository);
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
