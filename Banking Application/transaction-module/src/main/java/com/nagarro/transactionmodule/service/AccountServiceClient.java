package com.nagarro.transactionmodule.service;

import com.nagarro.transactionmodule.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ACCOUNT-MODULE")
public interface AccountServiceClient {

    @GetMapping("/v1/api/accounts/{accountNumber}")
    AccountDTO getAccountDetailsByAccountNumber(@PathVariable("accountNumber") String accountNumber);

    @PutMapping("/v1/api/accounts/{accountNumber}")
    AccountDTO updateBalance(@PathVariable("accountNumber") String accountNumber, @RequestBody double balance);
}
