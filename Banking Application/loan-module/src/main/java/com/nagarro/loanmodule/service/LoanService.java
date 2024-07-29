package com.nagarro.loanmodule.service;

import com.nagarro.loanmodule.entity.Loan;
import com.nagarro.loanmodule.request.LoginRequest;
import com.nagarro.loanmodule.request.VerifyStatusRequest;
import com.nagarro.loanmodule.response.LoginResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LoanService {
    Loan applyLoan(Loan loan);
    Loan verifyLoan(String loanId);
    void storeDocument(MultipartFile multipartFile, String loanId) throws IOException;
    Loan changeLoanStatus(VerifyStatusRequest statusRequest);
    List<Loan> getAllLoans();
    boolean login(LoginRequest loginRequest);
}
