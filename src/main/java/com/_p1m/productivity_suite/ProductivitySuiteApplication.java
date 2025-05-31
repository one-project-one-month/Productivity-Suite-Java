package com._p1m.productivity_suite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProductivitySuiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductivitySuiteApplication.class, args);
	}

}
