package com.projectLudoteca.ludoteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LudotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LudotecaApplication.class, args);
	}

}
