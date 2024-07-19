package com.nagarro.loanmodule.service.impl;

import com.nagarro.loanmodule.client.AccountClient;
import com.nagarro.loanmodule.client.UserClient;
import com.nagarro.loanmodule.dao.LoanDao;
import com.nagarro.loanmodule.dto.AccountDTO;
import com.nagarro.loanmodule.dto.User;
import com.nagarro.loanmodule.entity.Loan;
import com.nagarro.loanmodule.exception.BadRequestException;
import com.nagarro.loanmodule.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanDao loanDao;
    private final AccountClient accountClient;
    private final UserClient userClient;

    @Autowired
    public LoanServiceImpl(LoanDao loanDao, AccountClient accountClient, UserClient userClient) {
        this.loanDao = loanDao;
        this.accountClient = accountClient;
        this.userClient = userClient;
    }

    @Override
    public Loan applyLoan(Loan loan) {
        final AccountDTO accountDTO = accountClient.getAccountDetailsByAccountNumber(loan.getAccountNumber());
        if (!accountDTO.getEmail().equalsIgnoreCase(loan.getEmail())) {
            throw new BadRequestException("Invalid Email for userId", HttpStatus.BAD_REQUEST.value());
        }

        final User userDetails = userClient.getUserByEmail(loan.getEmail());
        if (!userDetails.getEmail().equalsIgnoreCase(loan.getEmail())) {
            throw new BadRequestException("Invalid Email for userId", HttpStatus.BAD_REQUEST.value());
        }
        loan.setLoanStatus("PENDING");
        loan.setVerificationStatus("PENDING");
        loan.setTimestamp(LocalDateTime.now());
        loan.setEmi(calculateEMI(loan.getLoanAmount(), loan.getTenure()));
        return loanDao.save(loan);
    }

    private double calculateEMI(double loanAmount, int tenure) {
        int n = tenure * 12;
        return (loanAmount * 0.006 * Math.pow((1 + 0.006), n)) / (Math.pow(1 + 0.006, n) - 1);
    }
}
