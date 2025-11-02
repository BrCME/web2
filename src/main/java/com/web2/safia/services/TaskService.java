package com.web2.safia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaTaskRepository;
import com.web2.safia.repositories.adapters.JpaWorkRepository;

@Service
public class TaskService {
	private static final Logger logger = LoggerFactory.getLogger(TaskServiceTest.class);

	private final JpaEmployeeRepository employeeRepository;
	private final JpaWorkRepository workRepository;
	private final JpaTaskRepository taskRepository;

	public TaskService(
			JpaEmployeeRepository employeeRepository,
			JpaWorkRepository workRepository,
			JpaTaskRepository taskRepository) {

		this.employeeRepository = employeeRepository;
		this.workRepository = workRepository;
		this.taskRepository = taskRepository;
	}

	
}
