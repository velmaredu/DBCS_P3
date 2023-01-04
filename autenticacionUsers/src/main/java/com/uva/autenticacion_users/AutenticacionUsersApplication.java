package com.uva.autenticacion_users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AutenticacionUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutenticacionUsersApplication.class, args);
	}

}
