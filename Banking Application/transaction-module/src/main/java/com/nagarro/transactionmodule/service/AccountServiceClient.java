package com.nagarro.transactionmodule.service;

import com.nagarro.transactionmodule.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ACCOUNT-MODULE")
public interface AccountServiceClient {

    @GetMapping("/v1/api/accounts/{accountNumber}")
    AccountDTO getAccountDetailsByAccountNumber(@PathVariable("accountNumber") int accountNumber);

    @PatchMapping("/v1/api/accounts/{accountNumber}")
    AccountDTO updateBalannce(@PathVariable("accountNumber") int accountNumber, @RequestBody double balance);
}
