package com.nagarro.transactionmodule.custom;

import com.nagarro.transactionmodule.dao.TransactionDao;
import com.nagarro.transactionmodule.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Endpoint(id = "transactions")
public class ActuatorEndpoint {

    @Autowired
    private TransactionDao transactionDao;

    @ReadOperation
    public List<Transaction> getAllTransactions() {
        return transactionDao.findAll();
    }
}
