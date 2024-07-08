package com.nagarro.transactionmodule.service;

import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.request.TransactionRequest;
import com.nagarro.transactionmodule.request.TransferRequest;

public interface TransactionService {
    AccountDTO depositMoney(TransactionRequest transactionRequest);

    AccountDTO withdrawMoney(TransactionRequest transactionRequest);

    AccountDTO transferMoney(TransferRequest transferRequest);
}
