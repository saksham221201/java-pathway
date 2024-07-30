package com.nagarro.kycmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KycModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(KycModuleApplication.class, args);
	}

}
