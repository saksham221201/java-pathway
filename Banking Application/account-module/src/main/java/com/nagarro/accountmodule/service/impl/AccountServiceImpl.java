package com.nagarro.accountmodule.service.impl;

import com.nagarro.accountmodule.constant.Constant;
import com.nagarro.accountmodule.dao.AccountDao;
import com.nagarro.accountmodule.dto.User;
import com.nagarro.accountmodule.entity.Account;
import com.nagarro.accountmodule.exception.BadRequestException;
import com.nagarro.accountmodule.exception.EmptyInputException;
import com.nagarro.accountmodule.exception.IllegalArgumentException;
import com.nagarro.accountmodule.exception.RecordNotFoundException;
import com.nagarro.accountmodule.service.AccountService;
import com.nagarro.accountmodule.client.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountDao accountDao;
    private final UserServiceClient userServiceClient;

    private final HashMap<Integer, String> map = new HashMap<>();

    @Autowired
    public AccountServiceImpl(AccountDao accountDao, UserServiceClient userServiceClient) {
        this.accountDao = accountDao;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Account createAccount(Account account) {
        map.put(1, Constant.SAVINGS);
        map.put(2, Constant.CURRENT);
        map.put(3, Constant.FIXED);

        // Checking if any of the fields is Empty
        if (account.getAccountType().isEmpty()) {
            logger.error("Inputs are blank in create Account");
            throw new EmptyInputException("Input cannot be Null!", HttpStatus.BAD_REQUEST.value());
        }

        // Checking if the accountType is valid or not
        if (!map.containsValue(account.getAccountType())) {
            logger.error("Account Type is incorrect");
            throw new IllegalArgumentException(
                    "AccountType must be " + Constant.SAVINGS + " or " + Constant.CURRENT + " or " + Constant.FIXED + "!", HttpStatus.BAD_REQUEST.value());
        }
        final User user = userServiceClient.getUserById(account.getUserId());
        if (!user.getEmail().equalsIgnoreCase(account.getEmail())) {
            throw new BadRequestException("Invalid Email for userId", HttpStatus.BAD_REQUEST.value());
        }
        return accountDao.save(account);
    }

    @Override
    public Account getAccountDetailsByAccountNumber(String accountNumber) {
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
    public Account updateBalance(double balance, String accountNumber){
        Account account = accountDao.findByAccountNumber(accountNumber).orElseThrow(()-> new RecordNotFoundException("Account not Found with accountNumber: " + accountNumber,
                HttpStatus.NOT_FOUND.value()));

        account.setBalance(balance);
        return accountDao.save(account);
    }
}
