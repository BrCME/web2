package com.web2.safia.auth.internal;

import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web2.safia.auth.api.RoleType;
import com.web2.safia.auth.api.dto.SignInUserRequestDto;
import com.web2.safia.auth.api.dto.SignUpUserRequestDto;
import com.web2.safia.auth.api.dto.SignUpUserResponseDto;
import com.web2.safia.auth.api.event.UserCreatedEvent;
import com.web2.safia.commit.api.event.SystemCommitOcurredEvent;
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.entity.CommitType;
import com.web2.safia.shared.entity.Employee;

@Service
public class AuthService extends BaseService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Value("${pepper:SAFIACustomPasswordEncoderPepperV1.0}")
	private String pepper;

	private final PasswordEncoder passwordEncoder;
	private final AuthRepository authRepository;

	public AuthService(
			ApplicationEventPublisher eventPublisher,
			PasswordEncoder passwordEncoder,
			AuthRepository authRepository) {

		super(eventPublisher);
		this.passwordEncoder = passwordEncoder;
		this.authRepository = authRepository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		return authRepository
				.findUserByUsername(username)
				.orElseThrow(() -> {
					logger.error("User with username '{}' not found", username);
					throw new UsernameNotFoundException(String.format("User with username '%s' not found", username));
				});
	}

	public SignUpUserResponseDto signUp(SignUpUserRequestDto requestDto) {
		var encodedPassword = passwordEncoder.encode(requestDto.password().concat(pepper));
		var user = new Employee(requestDto, encodedPassword);

		var roles = authRepository
				.findAllRolesByName(Set.of(RoleType.EMPLOYEE.name(), RoleType.NEWCOMER.name()));

		roles
				.stream()
				.forEach(user::addRole);

		authRepository.save(user);

		var newUser = new SignUpUserResponseDto(user);

		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("User created '%s'", newUser.username()),
						CommitType.CREATE,
						user));

		eventPublisher
				.publishEvent(new UserCreatedEvent(UUID.randomUUID(), requestDto.email()));

		return newUser;
	}

	public void signIn(SignInUserRequestDto requestDto) {
		authRepository
				.findUserByUsername(requestDto.username())
				.filter(employee -> passwordEncoder.matches(requestDto.password().concat(pepper),
						employee.getPassword()))
				.orElseThrow(() -> {
					logger.error("Invalid user credentials");
					throw new UsernameNotFoundException("Invalid user credentials");
				});

		eventPublisher
				.publishEvent(new UserCreatedEvent(UUID.randomUUID(), requestDto.username()));
	}
}
