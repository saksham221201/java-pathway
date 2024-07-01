package com.nagarro.transactionmodule.service.impl;

import com.nagarro.transactionmodule.dao.TransactionDao;
import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.dto.User;
import com.nagarro.transactionmodule.entity.Transaction;
import com.nagarro.transactionmodule.exception.BadRequestException;
import com.nagarro.transactionmodule.exception.InsufficientBalanceException;
import com.nagarro.transactionmodule.request.MoneyRequest;
import com.nagarro.transactionmodule.service.AccountServiceClient;
import com.nagarro.transactionmodule.service.TransactionService;
import com.nagarro.transactionmodule.service.UserServiceClient;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private AccountServiceClient accountServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private TransactionDao transactionDao;

    @Override
    @Transactional
    public AccountDTO depositMoney(MoneyRequest req) {
        logger.debug("Inside deposit Money");
        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(req.getAccountNumber());

        User user = userServiceClient.getUserByEmail(req.getEmail());

        accountDTO.setBalance(accountDTO.getBalance() + req.getAmount());
        logger.debug("Amount added");

        accountServiceClient.updateBalance(req.getAccountNumber(),accountDTO.getBalance());

        Transaction transaction = new Transaction();
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(req.getAmount());
        transaction.setAccountNumber(req.getAccountNumber());
        transaction.setBalance(accountDTO.getBalance());
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);
        logger.info("Deposit Transaction saved");

        return accountDTO;
    }

    @Override
    public AccountDTO withdrawMoney(MoneyRequest req) {
        logger.debug("Inside withdraw Money");
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

        accountServiceClient.updateBalance(req.getAccountNumber(),accountDTO.getBalance());

        Transaction transaction = new Transaction();
        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(req.getAmount());
        transaction.setAccountNumber(req.getAccountNumber());
        transaction.setBalance(accountDTO.getBalance());
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);
        logger.info("Withdrawal Transaction saved");
        return accountDTO;
    }
}
