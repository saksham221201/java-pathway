package com.nagarro.transactionmodule.service;

import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.dto.CardDTO;
import com.nagarro.transactionmodule.entity.Transaction;
import com.nagarro.transactionmodule.request.CardTransaction;
import com.nagarro.transactionmodule.request.TransactionRequest;
import com.nagarro.transactionmodule.request.TransferRequest;

import java.util.List;

public interface TransactionService {
    AccountDTO depositMoney(TransactionRequest transactionRequest);

    AccountDTO withdrawMoney(TransactionRequest transactionRequest);

    List<Transaction> getTransactions(String accountNumber);

    AccountDTO transferMoney(TransferRequest transferRequest);

    AccountDTO cardWithdrawal(CardTransaction cardTransaction);
}
