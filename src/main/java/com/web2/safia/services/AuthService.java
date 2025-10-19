package com.web2.safia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web2.safia.dtos.AuthLoginDto;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaRoleRepository;

@Service
public class AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	// private final BCryptPasswordEncoder passwordEncoder;
	private final JpaEmployeeRepository employeeRepository;
	private final JpaRoleRepository roleRepository;

	public AuthService(
			// BCryptPasswordEncoder passwordEncoder,
			JpaEmployeeRepository employeeRepository,
			JpaRoleRepository roleRepository) {

		// this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
		this.roleRepository = roleRepository;
	}

	public Employee login(AuthLoginDto authLoginDto) throws DomainException {
		var employee = employeeRepository.findByEmail(authLoginDto.email());

		if (!employee.isPresent()) {
			logger.error("Usuário do email '{}' não encontrado", authLoginDto.email());
			throw new DomainException("Usuário inválido");
		}
		
		// var passwordEncoded = passwordEncoder.encode(authLoginDto.password());
		var passwordEncoded = "123 de oliveira 4";

		if (employee.get().getPassword().equals(passwordEncoded)) {
			logger.error("Senha de '{}'' inválida", authLoginDto.email());
			throw new DomainException("Usuário inválido");
		}

		return employee.get();
	}
}
