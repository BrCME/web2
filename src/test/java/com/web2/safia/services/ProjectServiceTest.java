package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.repositories.adapters.JpaProjectRepository;

public class ProjectServiceTest {
	private ProjectService underTest;

	@Mock
	private JpaProjectRepository projectRepository;
	
	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new ProjectService(projectRepository);
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
