package com.nagarro.loanmodule.dao;

import com.nagarro.loanmodule.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanDao extends JpaRepository<Loan, String> {
    Optional<Loan> findByAccountNumber(String accountNumber);
}
