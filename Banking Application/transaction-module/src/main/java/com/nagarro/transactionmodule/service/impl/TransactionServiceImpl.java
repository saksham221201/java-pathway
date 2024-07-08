package com.nagarro.transactionmodule.service.impl;

import com.nagarro.transactionmodule.dao.TransactionDao;
import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.dto.User;
import com.nagarro.transactionmodule.entity.Transaction;
import com.nagarro.transactionmodule.exception.BadRequestException;
import com.nagarro.transactionmodule.exception.InsufficientBalanceException;
import com.nagarro.transactionmodule.request.TransactionRequest;
import com.nagarro.transactionmodule.request.TransferRequest;
import com.nagarro.transactionmodule.service.AccountServiceClient;
import com.nagarro.transactionmodule.service.TransactionService;
import com.nagarro.transactionmodule.service.UserServiceClient;
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

//        Transaction transaction = new Transaction();
//        transaction.setTransactionType("DEPOSIT");
//        transaction.setAmount(transactionRequest.getAmount());
//        transaction.setAccountNumber(transactionRequest.getAccountNumber());
//        transaction.setBalance(accountDTO.getBalance());
//        transaction.setTimestamp(LocalDateTime.now());
//
//        transactionDao.save(transaction);
        saveTransaction(transactionRequest,accountDTO.getBalance(),"DEPOSIT");
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

//        Transaction transaction = new Transaction();
//        transaction.setTransactionType("WITHDRAW");
//        transaction.setAmount(transactionRequest.getAmount());
//        transaction.setAccountNumber(transactionRequest.getAccountNumber());
//        transaction.setBalance(accountDTO.getBalance());
//        transaction.setTimestamp(LocalDateTime.now());
//
//        transactionDao.save(transaction);
        saveTransaction(transactionRequest,accountDTO.getBalance(),"WITHDRAW");
        logger.info("Withdrawal Transaction saved");
        return accountDTO;
    }

    @Override
    public AccountDTO transferMoney(TransferRequest transferRequest){
        logger.debug("Inside transfer Money");
        AccountDTO accountDTODebit = accountServiceClient.getAccountDetailsByAccountNumber(transferRequest.getDebitAccountNumber());

        if (!accountDTODebit.getEmail().equals(transferRequest.getEmail())) {
            logger.error("Invalid Account Details in transfer money");
            throw new BadRequestException("Invalid Account Details", HttpStatus.BAD_REQUEST.value());
        }

        double initialBalance = accountDTODebit.getBalance();

        // Checking if the amount to be withdrawn is greater than the present balance or not
        if (initialBalance < transferRequest.getAmount()) {
            logger.error("Not enough Balance!! Cannot transfer money");
            throw new InsufficientBalanceException("Not enough balance!! Cannot transfer money",
                    HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the amount to be transferred in the account is valid or not
        if (transferRequest.getAmount() <= 0) {
            logger.error("Please enter valid Amount in transfer money");
            throw new BadRequestException("Please enter valid Amount", HttpStatus.BAD_REQUEST.value());
        }

        accountDTODebit.setBalance(accountDTODebit.getBalance() - transferRequest.getAmount());

        accountServiceClient.updateBalance(transferRequest.getDebitAccountNumber(),accountDTODebit.getBalance());

//        Transaction transaction = new Transaction();
//        transaction.setTransactionType("WITHDRAW");
//        transaction.setAmount(transactionRequest.getAmount());
//        transaction.setAccountNumber(transactionRequest.getAccountNumber());
//        transaction.setBalance(accountDTO.getBalance());
//        transaction.setTimestamp(LocalDateTime.now());
//
//        transactionDao.save(transaction);
        TransactionRequest req1 = new TransactionRequest();
        req1.setAmount(transferRequest.getAmount());
        req1.setAccountNumber(transferRequest.getDebitAccountNumber());
        saveTransaction(req1,accountDTODebit.getBalance(),"TRANSFER WITHDRAWAL");
        logger.info("Transfer Withdrawal Transaction saved");

        // Transfer desposit
        TransactionRequest req2 = new TransactionRequest();

        AccountDTO accountDTOCredit = accountServiceClient.getAccountDetailsByAccountNumber(transferRequest.getCreditAccountNumber());
        accountDTOCredit.setBalance(accountDTOCredit.getBalance() + transferRequest.getAmount());
        logger.debug("Transfer money deposited");
        accountServiceClient.updateBalance(transferRequest.getCreditAccountNumber(),accountDTOCredit.getBalance());
        req2.setAmount(transferRequest.getAmount());
        req2.setAccountNumber(transferRequest.getCreditAccountNumber());
        saveTransaction(req2,accountDTOCredit.getBalance(),"TRANSFER DEPOSIT");
        logger.info("Transfer Deposit Transaction saved");

        return accountDTODebit;
    }

    private void saveTransaction(TransactionRequest req, Double balance, String transactionType){
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setAmount(req.getAmount());
        transaction.setAccountNumber(req.getAccountNumber());
        transaction.setBalance(balance);
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);
    }
}
