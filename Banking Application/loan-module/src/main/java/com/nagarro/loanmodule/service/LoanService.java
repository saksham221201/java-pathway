package com.nagarro.loanmodule.service;

import com.nagarro.loanmodule.entity.Loan;
import com.nagarro.loanmodule.request.VerifyStatusRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LoanService {
    Loan applyLoan(Loan loan);
    Loan verifyLoan(VerifyStatusRequest verifyStatusRequest);
    void storeDocument(MultipartFile multipartFile, String loanId) throws IOException;
    Loan checkLoanStatus(VerifyStatusRequest statusRequest);
    List<Loan> getAllLoans();
}
