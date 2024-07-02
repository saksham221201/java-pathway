package com.nagarro.transactionmodule.service;

import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.request.TransactionRequest;

public interface TransactionService {
    AccountDTO depositMoney(TransactionRequest transactionRequest);

    AccountDTO withdrawMoney(TransactionRequest transactionRequest);
}
