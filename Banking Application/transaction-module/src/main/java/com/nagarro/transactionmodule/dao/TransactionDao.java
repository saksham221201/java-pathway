package com.nagarro.transactionmodule.dao;

import com.nagarro.transactionmodule.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<UserTransaction, Long> {
}
