package com.web2.safia.safia.models.builder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web2.safia.safia.models.Customer;
import com.web2.safia.safia.models.builders.CustomerBuilder;

class CustomerBuilderTest {
	private CustomerBuilder underTest;

	@BeforeEach
	void setUp() {
		underTest = new CustomerBuilder();
	}

	@Test
	void givenEmptyArguments_whenBuilding_thenReturnEmptyCustomer() {
		Customer result = underTest
				.builder()
				.withEmail("luizinho@gmail.com")
				.withCreator(underTest.builder().build())
				.withPassword("null")
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
