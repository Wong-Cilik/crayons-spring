package com.crayons_2_0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan
//@Component
public class CrayonsSpringApplication {

	public static void main(String[] args) throws Exception  {
		SpringApplication.run(CrayonsSpringApplication.class, args);
	}
}
