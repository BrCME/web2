package com.web2.safia.services;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;

@Service
public class EmployeeService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceTest.class);

	private final JpaEmployeeRepository employeeRepository;
	
	public EmployeeService(JpaEmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Page<Employee> getAll(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	public Employee getById(UUID id) throws DomainException {
		Optional<Employee> employee = employeeRepository.findById(id);

		if (!employee.isPresent()) {
			throw new DomainException("Usuário não existe");
		}

		return employee.get();
	}

	public Employee getByEmail(String email) throws DomainException {
		Optional<Employee> employee = employeeRepository.findByEmail(email);

		if (!employee.isPresent()) {
			logger.error("Usuário com email '{}' não existe", email);
			throw new DomainException("Usuário não existe");
		}

		return employee.get();
	}

	public Set<Employee> getAllByCreator(Employee creator) {
		return employeeRepository.findAllByCreator(creator.getId());
	}
}
