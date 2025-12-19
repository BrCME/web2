package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaProjectRepository;
import com.web2.safia.repositories.adapters.JpaTaskRepository;
import com.web2.safia.repositories.adapters.JpaWorkRepository;

public class TaskServiceTest {
	private TaskService underTest;

	@Mock
	private CommitEventPublisher commitEventPublisher;
	
	@Mock
	private JpaEmployeeRepository employeeRepository;
	
	@Mock
	private JpaWorkRepository workRepository;
	
	@Mock
	private JpaTaskRepository taskRepository;

	@Mock
	private JpaProjectRepository projectRepository;
	
	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new TaskService(commitEventPublisher, employeeRepository, workRepository, taskRepository, projectRepository);
	}
	
}
