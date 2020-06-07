package com.github.rodbate.cloud.mini.config.examples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * example application
 *
 * @author rodbate
 * @since 2020/6/6
 */
@RestController
@SpringBootApplication
public class ExampleApplication {

	@Value("${test.name}")
	private String name;

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}


	@GetMapping("/test/name")
	public String getPropertyName() {
		return name;
	}
}
