package com.nagarro.accountmodule.service;

import com.nagarro.accountmodule.entity.Account;

public interface AccountService {
    Account createAccount(Account account);
    Account getAccountDetailsByAccountNumber(String accountNumber);
    Account updateBalance(double balance,String accountNumber);
}
