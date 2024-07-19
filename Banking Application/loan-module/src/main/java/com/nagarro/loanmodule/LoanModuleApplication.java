package com.nagarro.loanmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoanModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanModuleApplication.class, args);
	}

}
