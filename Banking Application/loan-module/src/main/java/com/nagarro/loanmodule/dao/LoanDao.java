package com.nagarro.loanmodule.dao;

import com.nagarro.loanmodule.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanDao extends JpaRepository<Loan, String> {
}
