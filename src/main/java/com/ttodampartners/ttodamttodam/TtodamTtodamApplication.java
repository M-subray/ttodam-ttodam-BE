package com.ttodampartners.ttodamttodam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TtodamTtodamApplication {

	public static void main(String[] args) {

		SpringApplication.run(TtodamTtodamApplication.class, args);
	}

}
