package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaProjectRepository;
import com.web2.safia.repositories.adapters.JpaTeamRepository;

public class ProjectServiceTest {
	private ProjectService underTest;

	@Mock
	private CommitEventPublisher commitEventPublisher;
	
	@Mock
	private JpaEmployeeRepository employeeRepository;
	
	@Mock
	private JpaProjectRepository projectRepository;
	
	@Mock
	private JpaTeamRepository teamRepository;
	
	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new ProjectService(commitEventPublisher, employeeRepository, projectRepository, teamRepository);
	}

	@Test
	void givenValidPageable_whenGetAll_thenReturnProjectPage() {
	}

	@Test
	void givenInvalidProjectId_whenAddEmployee_thenThrowException() {
	}

	@Test
	void givenValidProjectId_whenAddEmployee_thenReturnVoid() {
	}
}
