package com.web2.safia.auth.internal;

import java.util.Set;

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

import com.web2.safia.auth.api.dto.SignInUserRequest;
import com.web2.safia.auth.api.dto.SignUpUserRequest;
import com.web2.safia.auth.api.dto.SignUpUserResponse;
import com.web2.safia.auth.api.event.UserCreated;
import com.web2.safia.commit.api.event.SystemCommitOcurred;
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.entity.CommitType;
import com.web2.safia.shared.entity.Employee;
import com.web2.safia.shared.entity.RoleType;

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
					logger.debug("User with username '{}' not found", username);
					throw new UsernameNotFoundException(String.format("User with username '%s' not found", username));
				});
	}

	public SignUpUserResponse signUp(SignUpUserRequest request) {
		var encodedPassword = passwordEncoder.encode(request.password().concat(pepper));
		var user = new Employee(request, encodedPassword);

		var roles = authRepository
				.findAllRolesByName(Set.of(RoleType.EMPLOYEE.name(), RoleType.NEWCOMER.name()));

		roles
				.stream()
				.forEach(user::addRole);

		authRepository.save(user);

		var newUser = new SignUpUserResponse(user);

		eventPublisher.publishEvent(
				new SystemCommitOcurred(
						String.format("User created '%s'", newUser.username()),
						CommitType.CREATE,
						user));

		eventPublisher
				.publishEvent(new UserCreated(user.getId(), user.getEmail()));

		return newUser;
	}

	public void signIn(SignInUserRequest request) {
		authRepository
				.findUserByUsername(request.username())
				.filter(employee -> passwordEncoder.matches(request.password().concat(pepper),
						employee.getPassword()))
				.orElseThrow(() -> {
					logger.debug("Invalid user credentials");
					throw new UsernameNotFoundException("Invalid user credentials");
				});
	}
}
