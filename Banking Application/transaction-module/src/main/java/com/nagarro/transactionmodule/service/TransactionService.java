package com.nagarro.transactionmodule.service;

import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.request.MoneyRequest;

public interface TransactionService {
    AccountDTO depositMoney(MoneyRequest req);

    AccountDTO withdrawMoney(MoneyRequest req);
}
