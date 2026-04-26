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
import com.web2.safia.commit.api.event.SystemCommitOcurredEvent;
import com.web2.safia.employee.internal.Employee;
import com.web2.safia.project.internal.Project;
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.exception.DomainException;
import com.web2.safia.shared.exception.EntityNotFoundException;
import com.web2.safia.shared.exception.InputValidationException;
import com.web2.safia.task.api.dto.BriefTaskResponseDto;
import com.web2.safia.task.api.dto.CreateTaskRequestDto;
import com.web2.safia.task.api.dto.TaskResponseDto;
import com.web2.safia.task.api.dto.UpdateTaskRequestDto;

@Service
public class TaskService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

	private final TaskRepository taskRepository;

	public TaskService(
			ApplicationEventPublisher eventPublisher,
			TaskRepository taskRepository) {

		super(eventPublisher);
		this.taskRepository = taskRepository;
	}

	public Page<TaskResponseDto> getAll(Pageable pageable) {
		return taskRepository
				.findAll(pageable)
				.map(TaskResponseDto::new);
	}

	public Page<TaskResponseDto> getAllByIssuer(Pageable pageable, UUID issuerId) {
		return taskRepository
				.findAllByIssuerId(pageable, issuerId)
				.map(TaskResponseDto::new);
	}

	public Task getById(UUID id) {
		return taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found", id);
					throw new EntityNotFoundException(String.format("Task with id '%s' not found", id.toString()));
				});
	}

	public TaskResponseDto create(CreateTaskRequestDto requestDto, UUID issuerId) {
		var task = new Task(requestDto);

		var project = new Project(requestDto.projectId());
		task.setProject(project);

		var issuer = new Employee(issuerId);
		task.setCreator(issuer);

		taskRepository.save(task);
		logger.info("Criada atividade '{}' nova por '{}", task.getName(), issuerId);

		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Criada atividade '%s' novo", task.getId().toString()),
						CommitType.CREATE,
						issuer));

		return new TaskResponseDto(task);
	}

	public BriefTaskResponseDto promote(UUID id, UUID issuerId) {
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
				new SystemCommitOcurredEvent(
						String.format("Task with id '%s' promoted to '%s'", id, task.getStatus().name()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new BriefTaskResponseDto(task);
	}

	public BriefTaskResponseDto demote(UUID id, UUID issuerId) {
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
				new SystemCommitOcurredEvent(
						String.format("Task with id '%s' demoted to '%s'", id, task.getStatus().name()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new BriefTaskResponseDto(task);
	}

	public void deleteById(UUID id, UUID issuerId) {
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
				new SystemCommitOcurredEvent(
						String.format("Task with id '%s' deactivated", id),
						CommitType.DEACTIVATE,
						new Employee(issuerId)));
	}

	public BriefTaskResponseDto updateById(UUID id, UpdateTaskRequestDto requestDto, UUID issuerId) {
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
				.ifPresent(task::setDeadLine);

		requestDto
				.description()
				.ifPresent(task::setDescription);

		requestDto
				.name()
				.ifPresent(task::setName);

		requestDto
				.projectId()
				.ifPresent(projectId -> task.setProject(new Project(requestDto.projectId().get())));

		taskRepository.save(task);
		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Task with id '%s' updated", id.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new BriefTaskResponseDto(task);
	}
}
