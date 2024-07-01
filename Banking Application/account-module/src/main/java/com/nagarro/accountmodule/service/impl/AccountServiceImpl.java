package com.nagarro.accountmodule.service.impl;

import com.nagarro.accountmodule.constant.Constant;
import com.nagarro.accountmodule.dao.AccountDao;
import com.nagarro.accountmodule.entity.Account;
import com.nagarro.accountmodule.exception.EmptyInputException;
import com.nagarro.accountmodule.exception.IllegalArgumentException;
import com.nagarro.accountmodule.exception.RecordNotFoundException;
import com.nagarro.accountmodule.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final Random random = new Random();

    @Autowired
    private AccountDao accountDao;

    @Override
    public Account createAccount(Account account) {
        logger.debug("Inside create account");

        // Checking if any of the fields is Empty
        if (account.getAccountType().isEmpty()) {
            logger.error("Inputs are blank in create Account");
            throw new EmptyInputException("Input cannot be Null!", HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the accountType is valid or not
        if (!account.getAccountType().equalsIgnoreCase(Constant.SAVING)
                && !account.getAccountType().equalsIgnoreCase(Constant.CURRENT)
                && !account.getAccountType().equalsIgnoreCase(Constant.FIXED)) {
            logger.error("Account Type is incorrect");
            throw new IllegalArgumentException(
                    "AccountType must be " + Constant.SAVING + " or " + Constant.CURRENT + " or " + Constant.FIXED + "!", HttpStatus.BAD_REQUEST.value());
        }

        account.setAccountNumber(generateAccountNumber());

        logger.info("Account created");
        return accountDao.save(account);
    }

    @Override
    public Account getAccountDetailsByAccountNumber(int accountNumber) {
        logger.debug("Inside getAccountDetails");
        // Checking if the account with the accountNumber exists or not
        Optional<Account> accountOptional = accountDao.findByAccountNumber(accountNumber);
        if (accountOptional.isEmpty()) {
            logger.error("Account not found with account number{}", accountNumber);
            throw new RecordNotFoundException("Account not Found with accountNumber: " + accountNumber,
                    HttpStatus.NOT_FOUND.value());
        }

        return accountOptional.get();
    }

    @Override
    public Account updateBalance(double balance,int accountNumber){
        logger.debug("Inside updateBalance");

        if(accountDao.findByAccountNumber(accountNumber).isEmpty()){
            logger.error("Inside updateBalance-Account not found with account number: ", accountNumber);
        }
        Account account = accountDao.findByAccountNumber(accountNumber).orElseThrow(()-> new RecordNotFoundException("Account not Found with accountNumber: " + accountNumber,
                HttpStatus.NOT_FOUND.value()));

        account.setBalance(balance);
        return accountDao.save(account);
    }

    private int generateAccountNumber() {
        return 100000 + random.nextInt(900000);
    }
}
