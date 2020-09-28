package com.citi.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.citi.controller.TradeController;


@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.citi.*"})
@EnableJpaRepositories({"com.citi.repository"})
@EntityScan({"com.citi.entity"})
@EnableTransactionManagement
@SpringBootApplication
public class CitiFixedIncomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitiFixedIncomeApplication.class, args);
		Logger logger = LogManager.getLogger(CitiFixedIncomeApplication.class.getName());
		logger.info("main");

	}

}
