package com.web2.safia.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.models.Project;
import com.web2.safia.repositories.adapters.JpaProjectRepository;

@Service
public class ProjectService {
	private static final Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

	private final JpaProjectRepository projectRepository;
	

	public ProjectService(JpaProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	public Page<Project> getAll(Pageable pageable) {
		return projectRepository.findAll(pageable);
	}
	
	public void addEmployee(UUID projectId, Employee employee) throws DomainException {
		var project = projectRepository.findById(projectId);

		if (!project.isPresent()) {
			logger.error("Projeto com id {} não existe", projectId);
			throw new DomainException("Projeto não existe");
		}

		project.get().addEmployee(employee);
		projectRepository.save(project.get());
	}
}
