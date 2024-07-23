package com.nagarro.loanmodule.service.impl;

import com.nagarro.loanmodule.client.AccountClient;
import com.nagarro.loanmodule.client.UserClient;
import com.nagarro.loanmodule.dao.LoanDao;
import com.nagarro.loanmodule.dto.AccountDTO;
import com.nagarro.loanmodule.dto.User;
import com.nagarro.loanmodule.entity.Loan;
import com.nagarro.loanmodule.exception.BadRequestException;
import com.nagarro.loanmodule.request.VerifyStatusRequest;
import com.nagarro.loanmodule.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

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
        Loan newLoan = Loan
                .builder()
                .email(loan.getEmail())
                .accountNumber(loan.getAccountNumber())
                .mobile(loan.getMobile())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .tenure(loan.getTenure())
                .rateOfInterest(loan.getRateOfInterest())
                .loanStatus("PENDING")
                .verificationStatus("PENDING")
                .timestamp(LocalDateTime.now())
                .emi(calculateEMI(loan.getLoanAmount(),
                        loan.getTenure(), loan.getRateOfInterest()))
                .build();

        return loanDao.save(newLoan);
    }

    @Override
    public Loan verifyLoan(VerifyStatusRequest verifyStatusRequest) {

        Optional<Loan> loanOptional = loanDao.findById(verifyStatusRequest.getLoanId());
        if (loanOptional.isEmpty()) {
            throw new BadRequestException("Loan does not exist", HttpStatus.BAD_REQUEST.value());
        }
        loanOptional.get().setVerificationStatus(verifyStatusRequest.getVerifyStatus());
        return loanOptional.get();
    }

    @Override
    public void storeDocument(MultipartFile multipartFile, String loanId) throws IOException {
        Optional<Loan> existingLoan = loanDao.findById(loanId);
        if (existingLoan.isEmpty()) {
            throw new BadRequestException("Loan does not exists!!", HttpStatus.BAD_REQUEST.value());
        }
        Loan loan = existingLoan.get();
        byte[] content = multipartFile.getBytes();
        loan.setDocument(content);
        loanDao.save(loan);
    }

    @Override
    public Loan checkLoanStatus(VerifyStatusRequest statusRequest) {

        Optional<Loan> loanOptional = loanDao.findById(statusRequest.getLoanId());
        if (loanOptional.isEmpty()) {
            throw new BadRequestException("Loan does not exist", HttpStatus.BAD_REQUEST.value());
        }
        loanOptional.get().setLoanStatus(statusRequest.getVerifyStatus());
        return loanOptional.get();
    }

    private double calculateEMI(double loanAmount, int tenure, double rate) {
        int n = tenure * 12;
        double r = rate/1200;
        return (loanAmount * r * Math.pow((1 + r), n)) / (Math.pow(1 + r, n) - 1);
    }



}
