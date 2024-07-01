package com.nagarro.transactionmodule.service.impl;

import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.request.MoneyRequest;
import com.nagarro.transactionmodule.service.AccountServiceClient;
import com.nagarro.transactionmodule.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountServiceClient accountServiceClient;

    @Override
    public AccountDTO addMoney(MoneyRequest req) {
        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(req.getAccountNumber());
        accountDTO.setBalance(accountDTO.getBalance() + req.getAmount());
        return accountDTO;
    }

    @Override
    public AccountDTO withdrawMoney(MoneyRequest req) {
        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(req.getAccountNumber());
        accountDTO.setBalance(accountDTO.getBalance() - req.getAmount());
        return accountDTO;
    }
}
