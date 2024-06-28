package com.nagarro.accountmodule.service.impl;

import com.nagarro.accountmodule.dao.AccountDao;
import com.nagarro.accountmodule.entity.Account;
import com.nagarro.accountmodule.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Account createAccount(Account account) {
        return accountDao.save(account);
    }
}
