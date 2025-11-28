package com.web2.safia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaTaskRepository;
import com.web2.safia.repositories.adapters.JpaWorkRepository;

@Service
public class WorkService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

	private final JpaEmployeeRepository employeeRepository;
	private final JpaWorkRepository workRepository;
	private final JpaTaskRepository taskRepository;

	public WorkService(
			CommitEventPublisher commitEventPublisher,
			JpaEmployeeRepository employeeRepository,
			JpaWorkRepository workRepository,
			JpaTaskRepository taskRepository) {

		super(commitEventPublisher);
		this.employeeRepository = employeeRepository;
		this.workRepository = workRepository;
		this.taskRepository = taskRepository;
	}

}
