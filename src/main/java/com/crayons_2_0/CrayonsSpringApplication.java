package com.crayons_2_0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="com.crayons_2_0")
@SpringBootApplication
public class CrayonsSpringApplication {

	public static void main(String[] args) throws Exception  {
		
		SpringApplication.run(CrayonsSpringApplication.class, args);
	}
}
