package com.crayons_2_0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication
public class CrayonsSpringApplication {

	public static void main(String[] args) throws Exception  {
		SpringApplication.run(CrayonsSpringApplication.class, args);
	}
}
