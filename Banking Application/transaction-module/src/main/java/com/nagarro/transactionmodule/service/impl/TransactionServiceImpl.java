package com.nagarro.transactionmodule.service.impl;

import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.request.MoneyRequest;
import com.nagarro.transactionmodule.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Override
    public AccountDTO addMoney(MoneyRequest req) {
        return null;
    }

    @Override
    public AccountDTO withdrawMoney(MoneyRequest req) {
        return null;
    }
}
