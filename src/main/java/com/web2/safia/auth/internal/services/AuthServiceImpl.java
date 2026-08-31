package com.web2.safia.auth.internal;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web2.safia.auth.api.v1.dtos.SignInUserRequest;
import com.web2.safia.auth.api.v1.dtos.SignUpUserRequest;
import com.web2.safia.auth.api.v1.dtos.SignUpUserResponse;
import com.web2.safia.auth.api.v1.dtos.UserCredentialsResponse;
import com.web2.safia.auth.api.v1.dtos.UserRoleResponse;
import com.web2.safia.auth.api.v1.events.UserCreated;
import com.web2.safia.auth.api.v1.events.UserLoggedIn;
import com.web2.safia.auth.api.v1.events.UserLoggedOut;
import com.web2.safia.auth.api.v1.services.AuthService;
import com.web2.safia.auth.internal.mappers.RoleMapper;
import com.web2.safia.auth.internal.mappers.UserMapper;
import com.web2.safia.shared.exception.DomainException;
import com.web2.safia.shared.exception.EntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Value("${pepper:SAFIACustomPasswordEncoderPepperV1.0}")
	private String pepper;

	private final ApplicationEventPublisher eventPublisher;
	private final PasswordEncoder passwordEncoder;
	private final AuthRepository authRepository;
	private final UserMapper userMapper;
	private final RoleMapper roleMapper;

	public AuthServiceImpl(
			ApplicationEventPublisher eventPublisher,
			PasswordEncoder passwordEncoder,
			AuthRepository authRepository,
			UserMapper userMapper,
			RoleMapper roleMapper) {

		this.eventPublisher = eventPublisher;
		this.passwordEncoder = passwordEncoder;
		this.authRepository = authRepository;
		this.userMapper = userMapper;
		this.roleMapper = roleMapper;
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

	public SignUpUserResponse signUp(SignUpUserRequest requestBody) {
		logger.info("User trying to sign up: {}", requestBody);
		var encodedPassword = passwordEncoder.encode(requestBody.password().concat(pepper));
		var user = userMapper.toEntity(requestBody, encodedPassword);

		var roles = authRepository
				.findAllRolesByName(Set.of(Role.Type.EMPLOYEE.name(), Role.Type.NEWCOMER.name()));

		roles.forEach(user::addRole);

		authRepository.save(user);

		var newUser = userMapper.toSignUpUserResponse(user);

		eventPublisher
				.publishEvent(new UserCreated(
						user.getId(),
						requestBody.name(),
						requestBody.email(),
						requestBody.password(),
						requestBody.phoneNumber(),
						requestBody.cpf(),
						requestBody.birthDate()));

		return newUser;
	}

	public UserCredentialsResponse activate(SignInUserRequest requestBody) {
		logger.info("User to activate: {}", requestBody.username());
		var user = authRepository
				.findUserByUsername(requestBody.username())
				.orElseThrow(() -> {
					logger.debug("User not found");
					throw new EntityNotFoundException("Could not activate user");
				});

		if (user.isActive()) {
			logger.debug("User already activated");
			throw new DomainException("User already activated");
		}

		if (!passwordEncoder.matches(requestBody.password().concat(pepper), user.getPassword())) {
			logger.debug("Invalid user credentials");
			throw new DomainException("Could not activate user");
		}

		user.activate();
		authRepository.save(user);

		return new UserCredentialsResponse(user.getUsername(), user.getUsername());
	}

	public UserCredentialsResponse signIn(SignInUserRequest requestBody) {
		logger.info("User trying to sign in: {}", requestBody.username());
		var user = authRepository
				.findUserByUsername(requestBody.username())
				.orElseThrow(() -> {
					logger.debug("Invalid user credentials");
					throw new UsernameNotFoundException("Invalid user credentials");
				});

		if (!user.isActive() || !user.isEnabled() || !user.isAccountNonExpired() || !user.isAccountNonLocked()) {
			logger.debug("User is not enabled or is expired");
			throw new DomainException("User is not enabled or is expired");
		}

		if (!passwordEncoder.matches(requestBody.password().concat(pepper), user.getPassword())) {
			logger.debug("Invalid user credentials");
			throw new UsernameNotFoundException("Invalid user credentials");
		}

		this.eventPublisher
				.publishEvent(new UserLoggedIn(user.getId(), requestBody.username()));

		return new UserCredentialsResponse(requestBody.username(), requestBody.username());
	}

	public void signOut(HttpServletRequest request) {
		logger.warn("Logged user: {}", request.getUserPrincipal());
		logger.info("User trying to sign out: ");
		var user = authRepository
				.findUserByUsername(pepper)
				.orElseThrow(() -> {
					logger.debug("User with username '{}' not found", pepper);
					throw new UsernameNotFoundException(String.format("User with username '%s' not found", pepper));
				});

		this.eventPublisher
				.publishEvent(new UserLoggedOut(user.getId(), user.getUsername()));
	}

	public Page<UserRoleResponse> getRoles() {
		var roles = authRepository
				.findAllRoles()
				.stream()
				.map(roleMapper::toUserRoleResponse)
				.toList();

		return new PageImpl<>(roles);
	}
}
