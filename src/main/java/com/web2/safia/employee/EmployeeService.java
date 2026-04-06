package com.web2.safia.employee;

import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.common.BaseService;
import com.web2.safia.employee.dtos.EmployeeResponseDto;
import com.web2.safia.exceptions.InputValidationException;
import com.web2.safia.exceptions.EntityNotFoundException;

@Service
public class EmployeeService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	private final JpaEmployeeRepository employeeRepository;

	public EmployeeService(
			ApplicationEventPublisher eventPublisher,
			JpaEmployeeRepository employeeRepository) {

		super(eventPublisher);
		this.employeeRepository = employeeRepository;
	}

	public Page<EmployeeResponseDto> getAll(Pageable pageable) {
		return employeeRepository
				.findAll(pageable)
				.map(employee -> new EmployeeResponseDto(employee));
	}

	public EmployeeResponseDto getById(UUID id) throws InputValidationException, EntityNotFoundException {
		return employeeRepository
				.findById(id)
				.filter(employee -> employee.isEnabled())
				.map(employee -> new EmployeeResponseDto(employee))
				.orElseThrow(() -> {
					logger.error("Employee with id '{}' does not exists or is disabled", id);
					throw new EntityNotFoundException("Employee does not exists or is disabled");
				});
	}

	public EmployeeResponseDto getByEmail(String email) throws InputValidationException {
		return employeeRepository
				.findByEmail(email)
				.map(employee -> new EmployeeResponseDto(employee))
				.orElseThrow(() -> {
					logger.error("Employee with email '{}' does not exists or is disabled", email);
					throw new InputValidationException("Employee does not exists or is disabled");
				});
	}

	public Set<Employee> getAllByCreator(Employee creator) {
		return employeeRepository
				.findAllByCreator(creator.getId());
	}

	// @EventListener
	// public void onGetManagerByIdRequestEvent(GetManagerByIdRequestEvent eventRequest) throws EntityNotFoundException {
	// 	var manager = employeeRepository
	// 			.findById(eventRequest.id())
	// 			.orElseThrow(() -> {
	// 				logger.error("Employee with id '{}' does not exists or is disabled", eventRequest.id());
	// 				throw new EntityNotFoundException("Employee does not exists or is disabled");
	// 			});
		
	// 	eventPublisher.publishEvent(new GetManagerByIdResponseEvent(manager));
	// }

	// @EventListener
	// public void onGetEmployeeToAddByIdRequestEvent(GetEmployeeToAddByIdRequestEvent eventRequest) throws EntityNotFoundException {
	// 	var employeeToAdd = employeeRepository
	// 		.findById(eventRequest.id())
	// 		.orElseThrow(() -> {
	// 			logger.error("Employee with id '{}' does not exists or is disabled", eventRequest.id());
	// 			throw new EntityNotFoundException("Employee does not exists or is disabled");
	// 		});

	// 	eventPublisher.publishEvent(new GetEmployeeToAddByIdResponseEvent(employeeToAdd));
	// }

	// @EventListener
	// public void onGetEmployeeToRemoveByIdRequestEvent(GetEmployeeToRemoveByIdRequestEvent eventRequest) throws EntityNotFoundException {
	// 	var employeeToRemove = employeeRepository
	// 		.findById(eventRequest.id())
	// 		.orElseThrow(() -> {
	// 			logger.error("Employee with id '{}' does not exists or is disabled", eventRequest.id());
	// 			throw new EntityNotFoundException("Employee does not exists or is disabled");
	// 		});

	// 	eventPublisher.publishEvent(new GetEmployeeToRemoveByIdResponseEvent(employeeToRemove));
	// }
}
