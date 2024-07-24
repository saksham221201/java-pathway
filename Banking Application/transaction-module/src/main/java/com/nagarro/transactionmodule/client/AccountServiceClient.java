package com.nagarro.transactionmodule.client;

import com.nagarro.transactionmodule.config.FeignClientConfig;
import com.nagarro.transactionmodule.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ACCOUNT-MODULE", configuration = FeignClientConfig.class)
public interface AccountServiceClient {

    @GetMapping("/v1/api/accounts/{accountNumber}")
    AccountDTO getAccountDetailsByAccountNumber(@PathVariable("accountNumber") String accountNumber);

    @PutMapping("/v1/api/accounts/update/{accountNumber}")
    AccountDTO updateBalance(@PathVariable("accountNumber") String accountNumber, @RequestBody double balance);
}
