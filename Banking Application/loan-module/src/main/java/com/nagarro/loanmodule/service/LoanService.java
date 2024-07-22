package com.nagarro.loanmodule.service;

import com.nagarro.loanmodule.entity.Loan;
import com.nagarro.loanmodule.request.VerifyStatusRequest;

public interface LoanService {
    Loan applyLoan(Loan loan);
    Loan verifyLoan(VerifyStatusRequest verifyStatusRequest);

    
}
