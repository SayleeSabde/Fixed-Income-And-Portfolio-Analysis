package com.citi.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//import com.citi.repository.Security;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.citi.*")
@SpringBootApplication
public class CitiFixedIncomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitiFixedIncomeApplication.class, args);

	}

}
