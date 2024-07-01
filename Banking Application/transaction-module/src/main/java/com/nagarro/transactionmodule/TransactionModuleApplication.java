package com.nagarro.transactionmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class TransactionModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionModuleApplication.class, args);
	}

}