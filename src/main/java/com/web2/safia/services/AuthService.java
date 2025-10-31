package com.web2.safia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaRoleRepository;

import jakarta.validation.Valid;

@Service
public class AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	private final CommitEventPublisher commitEventPublisher;
	private final PasswordEncoder passwordEncoder;
	private final JpaEmployeeRepository employeeRepository;
	private final JpaRoleRepository roleRepository;

	public AuthService(
			CommitEventPublisher commitEventPublisher,
			PasswordEncoder passwordEncoder,
			JpaEmployeeRepository employeeRepository,
			JpaRoleRepository roleRepository) {

		this.commitEventPublisher = commitEventPublisher;
		this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
		this.roleRepository = roleRepository;
	}

	public Employee login(Employee employee) throws DomainException {
		var user = employeeRepository.findByEmail(employee.getEmail());

		if (!user.isPresent()) {
			logger.error("Usuário do email '{}' não encontrado", employee.getEmail());
			throw new DomainException("Usuário inválido");
		}

		if (!passwordEncoder.matches(employee.getPassword(), user.get().getPassword())) {
			logger.error("Senha de '{}' inválida", employee.getEmail());
			throw new DomainException("Usuário inválido");
		}

		logger.info("Login de '{}' efetuado!", employee.getEmail());
		return user.get();
	}

	public void create(@Valid Employee employee) {
		var encodedPassword = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(encodedPassword);

		employeeRepository.save(employee);

		commitEventPublisher.publishCreateCommitEvent(
				String.format("Criado usuário '%s' novo", employee.getEmail()),
				employee);
	}
}
