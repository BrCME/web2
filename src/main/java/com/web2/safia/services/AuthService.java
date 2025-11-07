package com.web2.safia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.models.Employee;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;

import jakarta.validation.Valid;

@Service
public class AuthService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	private final CommitEventPublisher commitEventPublisher;
	private final PasswordEncoder passwordEncoder;
	private final JpaEmployeeRepository employeeRepository;

	public AuthService(
			CommitEventPublisher commitEventPublisher,
			PasswordEncoder passwordEncoder,
			JpaEmployeeRepository employeeRepository) {

		this.commitEventPublisher = commitEventPublisher;
		this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var employee = employeeRepository.findByEmail(username);
		
		if (!employee.isPresent()) {
			logger.error("Empregado com email '{}' não encontrado", username);
			throw new UsernameNotFoundException(String.format("Empregado com email '%s' não encontrado", username));
		}

		return new User(employee.get().getName(), employee.get().getPassword(), employee.get().getAllRoles());
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
