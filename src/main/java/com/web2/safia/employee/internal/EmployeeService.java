package com.web2.safia.employee.internal;

import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.employee.api.dto.EmployeeResponse;
import com.web2.safia.shared.base.BaseEntity;
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.entity.Employee;
import com.web2.safia.shared.exception.EntityNotFoundException;
import com.web2.safia.shared.exception.InputValidationException;

@Service
public class EmployeeService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	private final EmployeeRepository employeeRepository;

	public EmployeeService(
			ApplicationEventPublisher eventPublisher,
			EmployeeRepository employeeRepository) {

		super(eventPublisher);
		this.employeeRepository = employeeRepository;
	}

	public Page<EmployeeResponse> getAll(Pageable pageable) {
		return employeeRepository
				.findAll(pageable)
				.map(EmployeeResponse::new);
	}

	public EmployeeResponse getById(UUID id) {
		return employeeRepository
				.findById(id)
				.filter(BaseEntity::isEnabled)
				.map(EmployeeResponse::new)
				.orElseThrow(() -> {
					logger.debug("Employee with id '{}' does not exists or is disabled", id);
					throw new EntityNotFoundException("Employee does not exists or is disabled");
				});
	}

	public EmployeeResponse getByEmail(String email) {
		return employeeRepository
				.findByEmail(email)
				.map(EmployeeResponse::new)
				.orElseThrow(() -> {
					logger.debug("Employee with email '{}' does not exists or is disabled", email);
					throw new InputValidationException("Employee does not exists or is disabled");
				});
	}

	public Set<Employee> getAllByCreator(Employee creator) {
		return employeeRepository
				.findAllByCreator(creator.getId());
	}
}
