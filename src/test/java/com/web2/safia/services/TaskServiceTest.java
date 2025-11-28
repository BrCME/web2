package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaTaskRepository;
import com.web2.safia.repositories.adapters.JpaWorkRepository;

public class TaskServiceTest {
	private TaskService underTest;

	@Mock
	private JpaEmployeeRepository employeeRepository;
	
	@Mock
	private JpaWorkRepository workRepository;
	
	@Mock
	private JpaTaskRepository taskRepository;
	
	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new TaskService(employeeRepository, workRepository, taskRepository);
	}
	
}
