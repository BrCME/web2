package com.web2.sofia.sofia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SofiaApplicationTests {

	@Test
	void contextLoads() {
		assertNotNull(getClass());
	}

	@Test
	void givenOne_whenAddNine_thenSumUpToTen() {
		Integer expected = 10;
		
		Integer one = 1;
		Integer nine = 9;

		assertEquals((one + nine), expected);
	}
}
