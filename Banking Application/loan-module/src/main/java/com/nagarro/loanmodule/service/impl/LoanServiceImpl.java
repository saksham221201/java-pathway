package com.nagarro.loanmodule.service.impl;

import com.nagarro.loanmodule.client.AccountClient;
import com.nagarro.loanmodule.client.UserServiceClient;
import com.nagarro.loanmodule.dao.LoanDao;
import com.nagarro.loanmodule.dto.AccountDTO;
import com.nagarro.loanmodule.dto.Mail;
import com.nagarro.loanmodule.dto.Role;
import com.nagarro.loanmodule.dto.User;
import com.nagarro.loanmodule.entity.Loan;
import com.nagarro.loanmodule.exception.BadRequestException;
import com.nagarro.loanmodule.request.LoginRequest;
import com.nagarro.loanmodule.request.VerifyStatusRequest;
import com.nagarro.loanmodule.response.LoginResponse;
import com.nagarro.loanmodule.service.LoanService;
import com.nagarro.loanmodule.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanDao loanDao;
    private final AccountClient accountClient;
    private final UserServiceClient userClient;
    private final MailService mailService;

    @Autowired
    public LoanServiceImpl(LoanDao loanDao, AccountClient accountClient, UserServiceClient userClient, MailService mailService) {
        this.loanDao = loanDao;
        this.accountClient = accountClient;
        this.userClient = userClient;
        this.mailService = mailService;
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

        sendMail(loan.getEmail(), "Loan Applied", "Loan of Rs " + loan.getLoanAmount() + " was applied in the Bank. The monthly EMI is " + calculateEMI(loan.getLoanAmount(), loan.getTenure(), loan.getRateOfInterest()) + " with a rate of interest of " + loan.getRateOfInterest() + " and tenure of " + loan.getTenure() + " years.");

        return loanDao.save(newLoan);
    }

    @Override
    public Loan verifyLoan(String loanId) {
        Optional<Loan> loanOptional = loanDao.findById(loanId);
        if (loanOptional.isEmpty()) {
            throw new BadRequestException("Loan does not exist", HttpStatus.BAD_REQUEST.value());
        }
        loanOptional.get().setVerificationStatus("Approved");

        Mail mail = new Mail();
        mail.setSubject("Loan Documents Approved");
        mail.setMessage("Your loan documents were approved by the Bank Manager");

        sendMail(loanOptional.get().getEmail(), "Loan Documents Approved", "Your loan documents were approved by the Bank Manager.");
        return loanDao.save(loanOptional.get());
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
    public Loan changeLoanStatus(VerifyStatusRequest statusRequest) {

        Optional<Loan> loanOptional = loanDao.findById(statusRequest.getLoanId());
        if (loanOptional.isEmpty()) {
            throw new BadRequestException("Loan does not exist", HttpStatus.BAD_REQUEST.value());
        }
        loanOptional.get().setLoanStatus(statusRequest.getVerifyStatus());
        return loanOptional.get();
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanDao.findAll();
    }

    @Override
    public boolean login(LoginRequest loginRequest) {
        final LoginResponse loginResponse = userClient.login(loginRequest);
        return loginResponse.getUser().getRole().equals(Role.ADMIN);
    }

    private double calculateEMI(double loanAmount, int tenure, double rate) {
        int n = tenure * 12;
        double r = rate / 1200;
        return (loanAmount * r * Math.pow((1 + r), n)) / (Math.pow(1 + r, n) - 1);
    }

    private void sendMail(String email, String subject, String message) {
        Mail mail = new Mail();
        mail.setSubject(subject);
        mail.setMessage(message);

        mailService.sendMail(email, mail);
    }
}
