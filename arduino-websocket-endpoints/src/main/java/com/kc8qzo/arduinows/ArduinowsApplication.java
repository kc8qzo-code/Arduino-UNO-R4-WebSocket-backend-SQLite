package com.kc8qzo.arduinows;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.util.TimeZone;

@SpringBootApplication
public class ArduinowsApplication {
	private static final String UTC = "UTC";

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(UTC));
		SpringApplication.run(ArduinowsApplication.class, args);
	}

	@Bean
	Clock clock() {
		return Clock.systemUTC();
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
