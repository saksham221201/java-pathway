package com.nagarro.transactionmodule.service.impl;

import com.nagarro.transactionmodule.dao.TransactionDao;
import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.dto.User;
import com.nagarro.transactionmodule.entity.Transaction;
import com.nagarro.transactionmodule.exception.BadRequestException;
import com.nagarro.transactionmodule.exception.InsufficientBalanceException;
import com.nagarro.transactionmodule.request.TransactionRequest;
import com.nagarro.transactionmodule.service.AccountServiceClient;
import com.nagarro.transactionmodule.service.TransactionService;
import com.nagarro.transactionmodule.service.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public AccountDTO depositMoney(TransactionRequest transactionRequest) {
        logger.debug("Inside deposit Money");

        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(transactionRequest.getAccountNumber());

        User user = userServiceClient.getUserByEmail(transactionRequest.getEmail());
        if (!accountDTO.getEmail().equals(transactionRequest.getEmail())) {
            logger.error("Invalid Account Details in deposit");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        accountDTO.setBalance(accountDTO.getBalance() + transactionRequest.getAmount());
        logger.debug("Amount added");

        accountServiceClient.updateBalance(transactionRequest.getAccountNumber(),accountDTO.getBalance());

        Transaction transaction = new Transaction();
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setAccountNumber(transactionRequest.getAccountNumber());
        transaction.setBalance(accountDTO.getBalance());
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);
        logger.info("Deposit Transaction saved");

        return accountDTO;
    }

    @Override
    public AccountDTO withdrawMoney(TransactionRequest transactionRequest) {
        logger.debug("Inside withdraw Money");
        AccountDTO accountDTO = accountServiceClient.getAccountDetailsByAccountNumber(transactionRequest.getAccountNumber());

        if (!accountDTO.getEmail().equals(transactionRequest.getEmail())) {
            logger.error("Invalid Account Details in withdraw money");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        double initialBalance = accountDTO.getBalance();

        // Checking if the amount to be withdrawn is greater than the present balance or not
        if (initialBalance < transactionRequest.getAmount()) {
            logger.error("Not enough Balance!! Cannot withdraw money");
            throw new InsufficientBalanceException("Not enough balance!! Cannot withdraw money",
                    HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the amount to be withdrawn in the account is valid or not
        if (transactionRequest.getAmount() <= 0) {
            logger.error("Please enter valid Amount in withdraw money");
            throw new BadRequestException("Please enter valid Amount", HttpStatus.BAD_REQUEST.value());
        }

        accountDTO.setBalance(accountDTO.getBalance() - transactionRequest.getAmount());

        accountServiceClient.updateBalance(transactionRequest.getAccountNumber(),accountDTO.getBalance());

        Transaction transaction = new Transaction();
        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setAccountNumber(transactionRequest.getAccountNumber());
        transaction.setBalance(accountDTO.getBalance());
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);
        logger.info("Withdrawal Transaction saved");
        return accountDTO;
    }

    @Override
    public List<Transaction> getTransactions(int accountNumber) {
        logger.debug("Inside Get Transactions by accountNumber");
        return transactionDao.findByAccountNumber(accountNumber);
    }
}
