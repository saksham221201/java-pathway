package com.nagarro.transactionmodule.service.impl;

import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.request.MoneyRequest;
import com.nagarro.transactionmodule.service.TransactionService;

public class TransactionSeviceImpl implements TransactionService {
    @Override
    public AccountDTO addMoney(MoneyRequest req) {
        AccountDTO account = null;
        if(req.getAmount()>0){
            account.setBalance(account.getBalance() + req.getAmount());
        }else {
            throw new IllegalArgumentException("Please provide a valid amount");
        }
        //send a patch request to account module to update balance
        return account;
    }

    @Override
    public AccountDTO withdrawMoney(MoneyRequest req) {
        return null;
    }
}
