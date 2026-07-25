package com.kc8qzo.arduinows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:sqlite::memory:")
class ArduinowsApplicationTests {

	@Test
	void contextLoads() {
	}
}
