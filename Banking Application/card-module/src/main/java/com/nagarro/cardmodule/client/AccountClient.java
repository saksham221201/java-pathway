package com.nagarro.cardmodule.client;

import com.nagarro.cardmodule.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {

    @GetMapping("/v1/api/accounts/{accountNumber}")
    AccountDTO getAccountDetailsByAccountNumber(@PathVariable int accountNumber);
}
