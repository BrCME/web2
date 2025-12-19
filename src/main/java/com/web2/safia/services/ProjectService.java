package com.web2.safia.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.models.Project;
import com.web2.safia.models.Team;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaProjectRepository;
import com.web2.safia.repositories.adapters.JpaTeamRepository;

import jakarta.validation.Valid;

@Service
public class ProjectService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

	private final JpaEmployeeRepository employeeRepository;
	private final JpaProjectRepository projectRepository;
	private final JpaTeamRepository teamRepository;

	public ProjectService(
			CommitEventPublisher commitEventPublisher,
			JpaEmployeeRepository employeeRepository,
			JpaProjectRepository projectRepository,
			JpaTeamRepository teamRepository) {

		super(commitEventPublisher);
		this.employeeRepository = employeeRepository;
		this.projectRepository = projectRepository;
		this.teamRepository = teamRepository;
	}

	public Page<Project> getAll(Pageable pageable) {
		return projectRepository.findAll(pageable);
	}

	public Page<Project> getAllByCreator(Pageable pageable, Employee creator) {
		return projectRepository.findAllByCreator(pageable, creator);
	}

	public Project getById(UUID id) throws DomainException {
		var project = projectRepository.findById(id);

		if (!project.isPresent()) {
			logger.error("Projeto com id '{}' não encontrado", id);
			throw new DomainException(String.format("Projeto com id '%s' não encontrado", id));
		}

		return project.get();
	}

	public void create(Team team, @Valid Project project, Employee creator) throws DomainException {
		var actualTeam = teamRepository.findById(team.getId());

		if (!actualTeam.isPresent()) {
			logger.error("Equipe com id '{}' não encontrada", team.getId());
			throw new DomainException(String.format("Equipe com id '%s' não encontrada", team.getId()));
		}

		if (StringUtils.hasText(project.getManager().getEmail())) {
			var manager = employeeRepository.findByEmail(project.getManager().getEmail());
			if (!manager.isPresent()) {
				logger.error("Gerente com email '{}' não encontrado", project.getManager().getEmail());
				throw new DomainException(String.format("Gerente com email '%s' não encontrado", project.getManager().getEmail()));
			}
			project.setManager(manager.get());
		}

		project.setId(null);
		project.setTeam(actualTeam.get());
		project.setCreator(creator);
		project.addEmployee(creator);
		projectRepository.save(project);
		logger.info("Criado projeto '{}' novo por '{}", project.getName(), creator.getEmail());

		commitEventPublisher.publishCreateCommitEvent(
				String.format("Criado projeto '%s' novo", project.getName()),
				creator);
	}

	public void deleteById(UUID id, Employee creator) throws DomainException {
		var project = projectRepository.findById(id);

		if (!project.isPresent()) {
			logger.error("Projeto '{}' não encontrado para deletar", id);
			throw new DomainException();
		}

		project.get().setDeletedAt(LocalDateTime.now());
		projectRepository.save(project.get());

		commitEventPublisher.publishDeactivateCommitEvent(
				String.format("Deletado projeto '%s'", project.get().getName()),
				creator);
	}

	public void updateById(@Valid Project project, Employee creator) throws DomainException {
		var actualProject = projectRepository.findById(project.getId());

		if (!actualProject.isPresent()) {
			logger.error("Projeto '{}' não encontrado para atualizar", project.getId());
			throw new DomainException("Projeto não encontrado");
		}

		var manager = employeeRepository.findByEmail(project.getManager().getEmail());

		if (!actualProject.isPresent()) {
			logger.error("Empregado '{}' não encontrado para atualizar como gerente", project.getManager().getEmail());
			throw new DomainException("Empregado gerente não encontrado");
		}

		actualProject.get().setName(project.getName());
		actualProject.get().setDescription(project.getDescription());
		actualProject.get().setManager(manager.get());

		projectRepository.save(actualProject.get());
		commitEventPublisher.publishUpdateCommitEvent(
				String.format("Atualizado projeto '%s'", actualProject.get().getName()),
				creator);
	}

	public void addEmployee(@Valid Project project, String employeeEmail, Employee creator) throws DomainException {
		var actualProject = projectRepository.findById(project.getId());

		if (!actualProject.isPresent()) {
			logger.error("Projeto '{}' não encontrado para adicionar empregado '{}'", project.getId(),
					employeeEmail);
			throw new DomainException("Projeto não encontrado para adicionar empregado");
		}

		var employee = employeeRepository.findByEmail(employeeEmail);

		if (!employee.isPresent()) {
			logger.error("Empregado '{}' não encontrado para adicionar ao projeto '{}'", employeeEmail, project.getName());
			throw new DomainException("Empregado não encontrado para adicionar ao projeto");
		}

		if (!actualProject.get().addEmployee(employee.get())) {
			logger.error("Não foi possível adicionar o empregado '{}' ao projeto '{}'", employee.get().getEmail(),
					actualProject.get().getName());
			throw new DomainException("Não foi possível adicionar empregado ao projeto");
		}

		projectRepository.save(actualProject.get());
		commitEventPublisher.publishUpdateCommitEvent(
				String.format("Atualizado projeto '%s' com novo empregado '%s'", actualProject.get().getName(),
						employee.get().getEmail()),
				creator);
	}

	public void removeEmployee(@Valid Project project, UUID employeeId, Employee creator) throws DomainException {
		var actualProject = projectRepository.findById(project.getId());
		var employee = employeeRepository.findById(employeeId);

		if (!employee.isPresent()) {
			logger.error("Empregado '{}' não encontrado para remover do projeto '{}'", employeeId, project.getName());
			throw new DomainException("Empregado não encontrado para remover do projeto");
		}

		if (!actualProject.isPresent()) {
			logger.error("Projeto '{}' não encontrado para remover empregado '{}'", project.getId(),
					employee.get().getEmail());
			throw new DomainException("Projeto não encontrado para remover empregado");
		}

		if (!actualProject.get().removeEmployee(employee.get())) {
			logger.error("Não foi possivel remover o empregado '{}' do projeto '{}'", employee.get().getEmail(),
					actualProject.get().getName());
			throw new DomainException("Não foi possível remover empregado do projeto");
		}

		projectRepository.save(actualProject.get());
		commitEventPublisher.publishUpdateCommitEvent(
				String.format("Atualizado projeto '%s'", actualProject.get().getName()),
				creator);
	}
}
