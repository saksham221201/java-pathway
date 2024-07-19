package com.nagarro.loanmodule.client;

import com.nagarro.loanmodule.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ACCOUNT-MODULE")
public interface AccountClient {

    @GetMapping("/v1/api/accounts/{accountNumber}")
    AccountDTO getAccountDetailsByAccountNumber(@PathVariable String accountNumber);
}
