package com.nagarro.accountmodule.controller;

import com.nagarro.accountmodule.entity.Account;
import com.nagarro.accountmodule.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/accounts")
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        logger.debug("Inside Account");
        Account newAccount = accountService.createAccount(account);
        logger.debug("New account created in controller");
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccountDetailsByAccountNumber(@PathVariable int accountNumber){
        logger.debug("Inside Get Account details");
        Account accountDetails = accountService.getAccountDetailsByAccountNumber(accountNumber);
        logger.debug("Account details fetched inside controller");
        return new ResponseEntity<>(accountDetails, HttpStatus.OK);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<Account> updateBalance(@PathVariable int accountNumber, @RequestBody double balance) {
        logger.debug("Inside updateBalance");
        Account account = accountService.updateBalance(balance, accountNumber);
        logger.debug("Balance updated");
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
