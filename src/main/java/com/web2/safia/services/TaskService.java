package com.web2.safia.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.models.Project;
import com.web2.safia.models.Task;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaProjectRepository;
import com.web2.safia.repositories.adapters.JpaTaskRepository;
import com.web2.safia.repositories.adapters.JpaWorkRepository;

import jakarta.validation.Valid;

@Service
public class TaskService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

	private final JpaEmployeeRepository employeeRepository;
	private final JpaWorkRepository workRepository;
	private final JpaTaskRepository taskRepository;
	private final JpaProjectRepository projectRepository;

	public TaskService(
			CommitEventPublisher commitEventPublisher,
			JpaEmployeeRepository employeeRepository,
			JpaWorkRepository workRepository,
			JpaTaskRepository taskRepository,
			JpaProjectRepository projectRepository) {

		super(commitEventPublisher);
		this.employeeRepository = employeeRepository;
		this.workRepository = workRepository;
		this.taskRepository = taskRepository;
		this.projectRepository = projectRepository;
	}

	public Page<Task> getAll(Pageable pageable) {
		return taskRepository.findAll(pageable);
	}

	public Page<Task> getAllByCreator(Pageable pageable, Employee creator) {
		return taskRepository.findAllByCreator(pageable, creator);
	}

	public Task getById(UUID id) throws DomainException {
		var task = taskRepository.findById(id);

		if (!task.isPresent()) {
			logger.error("Atividade com id '{}' não encontrado", id);
			throw new DomainException(String.format("Atividade com id '%s' não encontrado", id));
		}

		return task.get();
	}

	public void create(Project project, @Valid Task task, Employee creator) throws DomainException {
		var actualProject = projectRepository.findById(project.getId());

		if (!actualProject.isPresent()) {
			logger.error("Projeto com id '{}' não encontrado", project.getId());
			throw new DomainException(String.format("Projeto com id '%s' não encontrado", project.getId()));
		}
		
		task.setId(null);
		task.setProject(actualProject.get());
		task.setCreator(creator);
		taskRepository.save(task);
		logger.info("Criada atividade '{}' nova por '{}", task.getName(), creator.getEmail());

		commitEventPublisher.publishCreateCommitEvent(
				String.format("Criada atividade '%s' novo", task.getName()),
				creator);
	}

	public void deleteById(UUID id, Employee creator) throws DomainException {
		var task = taskRepository.findById(id);

		if (!task.isPresent()) {
			logger.error("Atividade '{}' não encontrada para deletar", id);
			throw new DomainException("Atividade não encontrada");
		}

		task.get().setDeletedAt(LocalDateTime.now());
		taskRepository.save(task.get());

		commitEventPublisher.publishDeactivateCommitEvent(
				String.format("Deletada atividade '%s'", task.get().getName()),
				creator);
	}

	public void updateById(@Valid Task task, Employee creator) throws DomainException {
		var actualTask = taskRepository.findById(task.getId());

		if (!actualTask.isPresent()) {
			logger.error("Atividade '{}' não encontrado para atualizar", task.getId());
			throw new DomainException("Atividade não encontrada");
		}

		actualTask.get().setName(task.getName());
		actualTask.get().setDescription(task.getDescription());
		actualTask.get().setDeadLine(task.getDeadLine());
		actualTask.get().setStatus(task.getStatus());

		taskRepository.save(actualTask.get());
		commitEventPublisher.publishUpdateCommitEvent(
				String.format("Atualizada atividade '%s'", actualTask.get().getName()),
				creator);
	}
}
