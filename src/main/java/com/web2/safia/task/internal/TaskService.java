package com.web2.safia.task.internal;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.commit.api.CommitType;
import com.web2.safia.commit.api.CreateCommitEvent;
import com.web2.safia.common.BaseService;
import com.web2.safia.employee.internal.Employee;
import com.web2.safia.exceptions.InputValidationException;
import com.web2.safia.task.api.BriefTaskResponseDto;
import com.web2.safia.task.api.CreateTaskRequestDto;
import com.web2.safia.task.api.TaskResponseDto;
import com.web2.safia.task.api.UpdateTaskRequestDto;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.exceptions.EntityNotFoundException;

@Service
public class TaskService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

	private final JpaTaskRepository taskRepository;

	public TaskService(
			ApplicationEventPublisher eventPublisher,
			JpaTaskRepository taskRepository) {

		super(eventPublisher);
		this.taskRepository = taskRepository;
	}

	public Page<TaskResponseDto> getAll(Pageable pageable) {
		return taskRepository
				.findAll(pageable)
				.map(task -> new TaskResponseDto(task));
	}

	public Page<TaskResponseDto> getAllByIssuer(Pageable pageable, Employee user) {
		return taskRepository
				.findAllByCreator(pageable, user)
				.map(task -> new TaskResponseDto(task));
	}

	public Task getById(UUID id) {
		return taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found", id);
					throw new EntityNotFoundException(String.format("Task with id '%s' not found", id.toString()));
				});
	}

	public TaskResponseDto create(CreateTaskRequestDto requestDto, Employee user) {

		// eventPublisher.publishEvent(new
		// GetProjectByIdRequestEvent(requestDto.projectId()));

		// var actualProject = projectRepository.findById();
		// if (!actualProject.isPresent()) {
		// logger.error("Projeto com id '{}' não encontrado", project.getId());
		// throw new DomainException(String.format("Projeto com id '%s' não encontrado",
		// project.getId()));
		// }

		var task = new Task(requestDto);
		// task.setProject(null);

		task.setId(null);
		task.setCreator(user);
		taskRepository.save(task);
		logger.info("Criada atividade '{}' nova por '{}", task.getName(), user.getEmail());

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Criada atividade '%s' novo", task.getId().toString()),
						CommitType.CREATE,
						user));

		return new TaskResponseDto(task);
	}

	public BriefTaskResponseDto promote(UUID id, Employee user) {
		var task = taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found to promote", id);
					throw new InputValidationException(
							String.format("Task with id '%s' not found to promote", id.toString()));
				});

		if (!task.promote()) {
			logger.error("Task with id '{}' already in '{}'", id, task.getStatus().name());
			throw new InputValidationException(
					String.format("Task with id '%s' already in '%s'", id, task.getStatus().name()));
		}

		taskRepository.save(task);
		logger.info("Task with id '{}' promoted to '{}'", id, task.getStatus().name());

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Task with id '%s' promoted to '%s'", id, task.getStatus().name()),
						CommitType.UPDATE,
						user));

		return new BriefTaskResponseDto(task);
	}

	public BriefTaskResponseDto demote(UUID id, Employee user) {
		var task = taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found to demote", id);
					throw new InputValidationException(
							String.format("Task with id '%s' not found to demote", id.toString()));
				});

		if (!task.demote()) {
			logger.error("Task with id '{}' already in '{}'", id, task.getStatus().name());
			throw new InputValidationException(
					String.format("Task with id '%s' already in '%s'", id, task.getStatus().name()));
		}

		taskRepository.save(task);
		logger.info("Task with id '{}' demoted to '{}'", id, task.getStatus().name());

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Task with id '%s' demoted to '%s'", id, task.getStatus().name()),
						CommitType.UPDATE,
						user));

		return new BriefTaskResponseDto(task);
	}

	public void deleteById(UUID id, Employee user) {
		var task = taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found to deactivate", id);
					throw new InputValidationException(
							String.format("Task with id '%s' not found to deactivate", id.toString()));
				});

		task.setDeletedAt(LocalDateTime.now());
		taskRepository.save(task);

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Task with id '%s' deactivated", id),
						CommitType.DEACTIVATE,
						user));
	}

	public BriefTaskResponseDto updateById(UUID id, UpdateTaskRequestDto requestDto, Employee user) {
		var task = taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found to update", id);
					throw new EntityNotFoundException(
							String.format("Task with id '%s' not found to update", id.toString()));
				});

		if (!task.isEnabled()) {
			logger.error("Task with id '{}' is not able to update", id);
			throw new DomainException(String.format("Task with id '%s' is not able to update", id.toString()));
		}

		requestDto
				.deadLine()
				.ifPresent(deadline -> task.setDeadLine(deadline));

		requestDto
				.description()
				.ifPresent(description -> task.setDescription(description));

		requestDto
				.name()
				.ifPresent(name -> task.setName(name));

		requestDto
				.projectId()
				.ifPresent(projectId -> logger.info("Project to update: {}", projectId));

		taskRepository.save(task);
		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Task with id '%s' updated", id.toString()),
						CommitType.UPDATE,
						user));

		return new BriefTaskResponseDto(task);
	}
}
