package com.nagarro.transactionmodule.dao;

import com.nagarro.transactionmodule.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<Transaction, Long> {
}
