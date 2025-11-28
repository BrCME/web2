package com.web2.safia.services;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.models.Employee;
import com.web2.safia.models.Role;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaRoleRepository;

import jakarta.validation.Valid;

@Service
public class AuthService implements UserDetailsService {
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

	@Override
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var employee = employeeRepository.findByEmail(username);

		if (!employee.isPresent()) {
			logger.error("Empregado com email '{}' não encontrado", username);
			throw new UsernameNotFoundException(String.format("Empregado com email '%s' não encontrado", username));
		}

		employee.get().getAllRoles();

		return employee.get();
	}

	public void create(@Valid Employee employee) {
		var encodedPassword = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(encodedPassword);

		var roles = roleRepository.findAllByType(Set.of(Role.Type.EMPLOYEE.name(), Role.Type.NEWCOMER.name()));

		roles.stream().forEach(role -> employee.addRole(role));

		employeeRepository.save(employee);

		commitEventPublisher.publishCreateCommitEvent(
				String.format("Criado usuário '%s' novo", employee.getEmail()),
				employee);
	}
}
