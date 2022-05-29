package com.learnjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearnJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnJwtApplication.class, args);
		System.out.println("Spring Boot Secured Application Started on port 8080");
	}

}
