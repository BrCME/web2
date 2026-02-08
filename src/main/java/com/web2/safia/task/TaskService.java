package com.web2.safia.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web2.safia.commit.Commit;
import com.web2.safia.commit.events.CreateCommitEvent;
import com.web2.safia.common.BaseService;
import com.web2.safia.employee.Employee;
import com.web2.safia.employee.JpaEmployeeRepository;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.exceptions.EntityNotFoundException;
import com.web2.safia.project.JpaProjectRepository;
import com.web2.safia.project.Project;
import com.web2.safia.task.dtos.BriefTaskResponseDto;
import com.web2.safia.task.dtos.CreateTaskRequestDto;
import com.web2.safia.task.dtos.TaskResponseDto;
import com.web2.safia.work.JpaWorkRepository;

import jakarta.validation.Valid;

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

	public Task getById(UUID id) throws EntityNotFoundException {
		return taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found", id);
					throw new EntityNotFoundException(String.format("Task with id '%s' not found", id.toString()));
				});
	}

	public void create(CreateTaskRequestDto requestDto, Employee user) throws DomainException {

		eventPublisher.publishEvent(new GetProjectByIdRequestEvent(requestDto.projectId()));
		
		// var actualProject = projectRepository.findById();
		// if (!actualProject.isPresent()) {
		// 	logger.error("Projeto com id '{}' não encontrado", project.getId());
		// 	throw new DomainException(String.format("Projeto com id '%s' não encontrado", project.getId()));
		// }

		var task = new Task(requestDto);
		// task.setProject(null);

		task.setId(null);
		task.setCreator(user);
		taskRepository.save(task);
		logger.info("Criada atividade '{}' nova por '{}", task.getName(), creator.getEmail());

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Criada atividade '%s' novo", task.getId().toString()),
						Commit.Type.CREATE,
						user));
	}

	public BriefTaskResponseDto promote(UUID id, Employee user) throws DomainException {
		var task = taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found to promote", id);
					throw new DomainException(String.format("Task with id '%s' not found to promote", id.toString()));
				});

		if (!task.promote()) {
			logger.error("Task with id '{}' already in '{}'", id, task.getStatus().name());
			throw new DomainException(String.format("Task with id '%s' already in '%s'", id, task.getStatus().name()));
		}

		taskRepository.save(task);
		logger.info("Task with id '{}' promoted to '{}'", id, task.getStatus().name());

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Task with id '%s' promoted to '%s'", id, task.getStatus().name()),
						Commit.Type.UPDATE,
						user));

		return new BriefTaskResponseDto(task);
	}

	public BriefTaskResponseDto demote(UUID id, Employee user) throws DomainException {
		var task = taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found to demote", id);
					throw new DomainException(String.format("Task with id '%s' not found to demote", id.toString()));
				});

		if (!task.demote()) {
			logger.error("Task with id '{}' already in '{}'", id, task.getStatus().name());
			throw new DomainException(String.format("Task with id '%s' already in '%s'", id, task.getStatus().name()));
		}

		taskRepository.save(task);
		logger.info("Task with id '{}' demoted to '{}'", id, task.getStatus().name());

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Task with id '%s' demoted to '%s'", id, task.getStatus().name()),
						Commit.Type.UPDATE,
						user));

		return new BriefTaskResponseDto(task);
	}

	public void deleteById(UUID id, Employee user) throws DomainException {
		var task = taskRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Task with id '{}' not found to deactivate", id);
					throw new DomainException(
							String.format("Task with id '%s' not found to deactivate", id.toString()));
				});

		task.setDeletedAt(LocalDateTime.now());
		taskRepository.save(task);

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Task with id '%s' deactivated", id),
						Commit.Type.DEACTIVATE,
						user));
	}

	public void updateById(@Valid Task task, Employee user) throws DomainException {
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
