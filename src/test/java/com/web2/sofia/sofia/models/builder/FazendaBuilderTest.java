package com.web2.sofia.sofia.models.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web2.sofia.sofia.models.Fazenda;
import com.web2.sofia.sofia.models.builders.FazendaBuilder;

class FazendaBuilderTest {
	private FazendaBuilder underTest;

	@BeforeEach
	void setUp() {
		underTest = new FazendaBuilder();
	}

	@Test
	void givenValidArguments_whenBuilding_thenReturnExpectedObject() {
		String validNome = "Fazenda Nova";

		Fazenda expected = new Fazenda(validNome);
		Fazenda result = underTest
				.builder()
				.withNome(validNome)
				.build();

		assertNotNull(result);
		assertEquals(expected.getNome(), result.getNome());
	}

	@Test
	void givenInvalidArguments_whenBuilding_thenThrowException() {
		String invalidNome = null;

		assertThrowsExactly(IllegalArgumentException.class, () -> underTest
				.builder()
				.withNome(invalidNome)
				.build());
	}

	@Test
	void givenEmptyArguments_whenBuilding_thenReturnEmptyObject() {
		Fazenda result = underTest.builder().build();

		assertNotNull(result);
		assertNull(result.getNome());
	}
}
