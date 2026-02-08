package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.commit.CommitEventPublisher;
import com.web2.safia.project.JpaProjectRepository;
import com.web2.safia.safia.employee.JpaEmployeeRepository;
import com.web2.safia.task.JpaTaskRepository;
import com.web2.safia.task.TaskService;
import com.web2.safia.work.JpaWorkRepository;

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
