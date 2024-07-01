package com.nagarro.transactionmodule.service.impl;

import com.nagarro.transactionmodule.dao.TransactionDao;
import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.entity.UserTransaction;
import com.nagarro.transactionmodule.exception.BadRequestException;
import com.nagarro.transactionmodule.exception.InsufficientBalanceException;
import com.nagarro.transactionmodule.request.MoneyRequest;
import com.nagarro.transactionmodule.service.AccountServiceClient;
import com.nagarro.transactionmodule.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountServiceClient accountServiceClient;

    @Autowired
    private TransactionDao transactionDao;

    @Override
    @Transactional
    public AccountDTO depositMoney(MoneyRequest req) {
        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(req.getAccountNumber());
        accountDTO.setBalance(accountDTO.getBalance() + req.getAmount());
        accountServiceClient.updateBalannce(req.getAccountNumber(),accountDTO.getBalance());
        UserTransaction transaction = new UserTransaction();
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(req.getAmount());
        transaction.setAccountNumber(req.getAccountNumber());
        transaction.setBalance(accountDTO.getBalance());
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);

        return accountDTO;
    }

    @Override
    public AccountDTO withdrawMoney(MoneyRequest req) {
        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(req.getAccountNumber());

        double initialBalance = accountDTO.getBalance();

        // Checking if the amount to be withdrawn is greater than the present balance or not
        if (initialBalance < req.getAmount()) {
            throw new InsufficientBalanceException("Not enough balance!! Cannot withdraw money",
                    HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the amount to be withdrawn in the account is valid or not
        if (req.getAmount() <= 0) {
            throw new BadRequestException("Please enter valid Amount", HttpStatus.BAD_REQUEST.value());
        }
        accountDTO.setBalance(accountDTO.getBalance() - req.getAmount());
        accountServiceClient.updateBalannce(req.getAccountNumber(),accountDTO.getBalance());
        UserTransaction transaction = new UserTransaction();
        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(req.getAmount());
        transaction.setAccountNumber(req.getAccountNumber());
        transaction.setBalance(accountDTO.getBalance());
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);
        return accountDTO;
    }
}
