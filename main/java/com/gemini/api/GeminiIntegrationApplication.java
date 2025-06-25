package com.gemini.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class GeminiIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeminiIntegrationApplication.class, args);

		System.out.println("The system has been started");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	} // Configure ObjectMapper as a Spring Bean (for JSON serialization/deserialization)
	@Bean public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
