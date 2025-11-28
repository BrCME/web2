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
import com.web2.safia.models.Task;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaTaskRepository;
import com.web2.safia.repositories.adapters.JpaWorkRepository;

import jakarta.validation.Valid;

@Service
public class TaskService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

	private final JpaEmployeeRepository employeeRepository;
	private final JpaWorkRepository workRepository;
	private final JpaTaskRepository taskRepository;

	public TaskService(
			CommitEventPublisher commitEventPublisher,
			JpaEmployeeRepository employeeRepository,
			JpaWorkRepository workRepository,
			JpaTaskRepository taskRepository) {

		super(commitEventPublisher);
		this.employeeRepository = employeeRepository;
		this.workRepository = workRepository;
		this.taskRepository = taskRepository;
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

	public void create(@Valid Task task, Employee creator) {
		task.setCreator(creator);
		task.addEmployee(creator);
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

	public void addEmployee(@Valid Task task, UUID employeeId, Employee creator) throws DomainException {
		var actualTask = taskRepository.findById(task.getId());
		var employee = employeeRepository.findById(employeeId);

		if (!employee.isPresent()) {
			logger.error("Empregado '{}' não encontrado para adicionar à atividade '{}'", employeeId, task.getName());
			throw new DomainException("Empregado não encontrado para adicionar à atividade");
		}

		if (!actualTask.isPresent()) {
			logger.error("Atividade '{}' não encontrada para adicionar empregado '{}'", task.getId(),
					employee.get().getEmail());
			throw new DomainException("Atividade não encontrada para adicionar empregado");
		}

		if (!actualTask.get().addEmployee(creator)) {
			logger.error("Não foi possível adicionar o empregado '{}' à equipe '{}'", employee.get().getEmail(),
					actualTask.get().getName());
			throw new DomainException("Não foi possível adicionar empregado à equipe");
		}

		taskRepository.save(actualTask.get());
		commitEventPublisher.publishUpdateCommitEvent(
				String.format("Atualizada atividade '%s' com novo empregado '%s'", actualTask.get().getName(),
						employee.get().getEmail()),
				creator);
	}

	public void removeEmployee(@Valid Task task, UUID employeeId, Employee creator) throws DomainException {
		var actualTask = taskRepository.findById(task.getId());
		var employee = employeeRepository.findById(employeeId);

		if (!employee.isPresent()) {
			logger.error("Empregado '{}' não encontrado para remover da atividade '{}'", employeeId, task.getName());
			throw new DomainException("Empregado não encontrado para remover da atividade");
		}

		if (!actualTask.isPresent()) {
			logger.error("Atividade '{}' não encontrado para remover empregado '{}'", task.getId(),
					employee.get().getEmail());
			throw new DomainException("Atividade não encontrado para remover empregado");
		}

		if (!actualTask.get().removeEmployee(employee.get())) {
			logger.error("Não foi possivel remover o empregado '{}' da atividade '{}'", employee.get().getEmail(),
					actualTask.get().getName());
			throw new DomainException("Não foi possível remover empregado da atividade");
		}

		taskRepository.save(actualTask.get());
		commitEventPublisher.publishUpdateCommitEvent(
				String.format("Atualizada atividade '%s'", actualTask.get().getName()),
				creator);
	}
}
