package com.web2.safia.configs;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.web2.safia.models.Employee;

@Configuration
public class SecurityConfig implements AuditorAware<Employee> {
	private static final String[] WHITE_LIST = { "/**" };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(WHITE_LIST).permitAll())
				.formLogin(form -> form.loginPage("/login").permitAll())
				.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public @NonNull Optional<Employee> getCurrentAuditor() {
		return Optional
				.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal)
				.map(Employee.class::cast);
	}

	// @Bean
	// UserDetailsService userDetailsService(DataSource dataSource) {
	// JdbcUserDetailsManager userDetailsManager = new
	// JdbcUserDetailsManager(dataSource);

	// userDetailsManager.setUsersByUsernameQuery(
	// "SELECT e.name, e.email, e.birth_date, (e.deleted_at IS NULL) FROM employee e
	// WHERE e.email LIKE ?");
	// userDetailsManager.setAuthoritiesByUsernameQuery(
	// "SELECT e.email, r.type FROM employee e INNER JOIN role_to_employee rte ON
	// rte.employee_id = e.id INNJER role r ON rte.role_id = r.id JOIN WHERE e.email
	// LIKE ?");
	// userDetailsManager.setRolePrefix("ROLE_");

	// return userDetailsManager;
	// }
}
