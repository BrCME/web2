package com.web2.safia.auth.internal;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web2.safia.auth.api.RoleType;
import com.web2.safia.auth.api.SignInUserRequestDto;
import com.web2.safia.auth.api.SignUpUserRequestDto;
import com.web2.safia.auth.api.SignUpUserResponseDto;
import com.web2.safia.commit.api.CommitType;
import com.web2.safia.commit.api.CreateCommitEvent;
import com.web2.safia.employee.internal.Employee;
import com.web2.safia.employee.internal.JpaEmployeeRepository;
import com.web2.safia.employee.internal.JpaRoleRepository;
import com.web2.safia.shared.base.BaseService;

@Service
public class AuthService extends BaseService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	private final PasswordEncoder passwordEncoder;
	private final JpaEmployeeRepository employeeRepository;
	private final JpaRoleRepository roleRepository;

	public AuthService(
			ApplicationEventPublisher eventPublisher,
			PasswordEncoder passwordEncoder,
			JpaEmployeeRepository employeeRepository,
			JpaRoleRepository roleRepository) {

		super(eventPublisher);
		this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		return employeeRepository
				.findByEmail(username)
				.orElseThrow(() -> {
					logger.error("User with username '{}' not found", username);
					throw new UsernameNotFoundException(String.format("User with username '%s' not found", username));
				});
	}

	public SignUpUserResponseDto signUp(SignUpUserRequestDto requestDto) {
		var encodedPassword = passwordEncoder.encode(requestDto.password());
		var user = new Employee(requestDto, encodedPassword);

		var roles = roleRepository
				.findAllByTypes(Set.of(RoleType.EMPLOYEE.name(), RoleType.NEWCOMER.name()));

		roles
				.stream()
				.forEach(role -> user.addRole(role));

		employeeRepository.save(user);

		var newUser = new SignUpUserResponseDto(user);

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("User created '%s'", newUser.username()),
						CommitType.CREATE,
						user));

		return newUser;
	}

	public void signIn(SignInUserRequestDto requestDto) {
		loadUserByUsername(requestDto.username());
	}
}
