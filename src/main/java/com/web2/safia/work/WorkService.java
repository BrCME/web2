package com.web2.safia.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.commit.CommitEventPublisher;
import com.web2.safia.common.BaseService;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.safia.employee.Employee;
import com.web2.safia.task.JpaTaskRepository;
import com.web2.safia.task.Task;

import jakarta.validation.Valid;

@Service
public class WorkService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(WorkService.class);

	private final JpaWorkRepository workRepository;
	private final JpaTaskRepository taskRepository;

	public WorkService(
			CommitEventPublisher commitEventPublisher,
			JpaWorkRepository workRepository,
			JpaTaskRepository taskRepository) {

		super(commitEventPublisher);
		this.workRepository = workRepository;
		this.taskRepository = taskRepository;
	}

	public Page<Work> getAll(Pageable pageable) {
		return workRepository.findAll(pageable);
	}

	public Page<Work> getAllByCreator(Pageable pageable, Employee creator) {
		return workRepository.findAllByEmployee(pageable, creator);
	}
	
	public void create(@Valid Work work, Task task, Employee creator) throws DomainException {
		var actualTask = taskRepository.findById(task.getId());

		if (!actualTask.isPresent()) {
			logger.error("Atividade com id '{}' não encontrada", task.getId());
			throw new DomainException(String.format("Atividade com id '%s' não encontrada", task.getId()));
		}

		work.setId(null);
		work.setTask(actualTask.get());
		work.setEmployee(creator);
		workRepository.save(work);
		logger.info("Criado trabalho '{}' novo por '{}", work.getDescription(), creator.getEmail());

		commitEventPublisher.publishCreateCommitEvent(
				String.format("Criado trabalho '%s' novo", work.getDescription()),
				creator);
	}
}
