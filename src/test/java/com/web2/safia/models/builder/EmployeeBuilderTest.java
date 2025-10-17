package com.web2.safia.models.builder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web2.safia.models.Employee;
import com.web2.safia.models.builders.EmployeeBuilder;

class EmployeeBuilderTest {
	private EmployeeBuilder underTest;

	@BeforeEach
	void setUp() {
		underTest = new EmployeeBuilder();
	}

	@Test
	void givenEmptyArguments_whenBuilding_thenReturnEmptyEmployee() {
		Employee result = underTest
				.builder()
				.build();

		assertNotNull(result);
		assertNull(result.getId());
		assertNull(result.getName());
		assertNull(result.getEmail());
		assertNull(result.getPassword());
		assertNull(result.getBirthDate());
		assertNull(result.getCreator());
		assertNull(result.getCreatedAt());
		assertNull(result.getUpdatedAt());
		assertNull(result.getDeletedAt());
	}
}
