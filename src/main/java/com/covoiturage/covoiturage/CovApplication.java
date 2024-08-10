package com.covoiturage.covoiturage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication
public class CovApplication {
	public static void main(String[] args) {
		SpringApplication.run(CovApplication.class, args);
		System.out.println("http://localhost:8090");
	}
}