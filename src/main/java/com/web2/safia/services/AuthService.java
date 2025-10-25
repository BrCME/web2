package com.web2.safia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	// private final BCryptPasswordEncoder passwordEncoder;
	private final JpaEmployeeRepository employeeRepository;
	private final JpaRoleRepository roleRepository;

	public AuthService(
			CommitEventPublisher commitEventPublisher,
			// BCryptPasswordEncoder passwordEncoder,
			JpaEmployeeRepository employeeRepository,
			JpaRoleRepository roleRepository) {

		this.commitEventPublisher = commitEventPublisher;
		// this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
		this.roleRepository = roleRepository;
	}

	public Employee login(Employee employee) throws DomainException {
		var user = employeeRepository.findByEmail(employee.getEmail());

		if (!user.isPresent()) {
			logger.error("Usuário do email '{}' não encontrado", employee.getEmail());
			throw new DomainException("Usuário inválido");
		}

		// var passwordEncoded = passwordEncoder.encode(authLoginDto.password());
		var passwordEncoded = "123 de oliveira 4";

		if (user.get().getPassword().equals(passwordEncoded)) {
			logger.error("Senha de '{}'' inválida", employee.getEmail());
			throw new DomainException("Usuário inválido");
		}

		return user.get();
	}

	public void create(@Valid Employee employee) {
		employeeRepository.save(employee);

		commitEventPublisher
				.publishCreateCommitEvent(String.format("Criado usuário '%s' novo", employee.getEmail()), null);

		return;
	}
}
