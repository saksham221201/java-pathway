package com.nagarro.loanmodule.controller;

import com.nagarro.loanmodule.entity.Loan;
import com.nagarro.loanmodule.request.VerifyStatusRequest;
import com.nagarro.loanmodule.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("message", "Welcome to Loan Application!");
        return "index";
    }

    @PostMapping("/apply")
    public ResponseEntity<Loan> applyLoan(@RequestBody Loan loan) {
        Loan appliedLoan = loanService.applyLoan(loan);
        return new ResponseEntity<>(appliedLoan, HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("loanId") String loanId, Model model) throws IOException {
        loanService.storeDocument(file, loanId);
        return "uploadSuccess";
    }

    @PostMapping("/verify")
    public ResponseEntity<Loan> verifyLoan(@RequestBody VerifyStatusRequest verifyStatusRequest){
        Loan loan = loanService.verifyLoan(verifyStatusRequest);

        return new ResponseEntity<>(loan,HttpStatus.OK);
    }

    @PostMapping("/loanStatus")
    public ResponseEntity<Loan> loanStatus(@RequestBody VerifyStatusRequest verifyStatusRequest){
        Loan loan = loanService.checkLoanStatus(verifyStatusRequest);

        return new ResponseEntity<>(loan,HttpStatus.OK);
    }

    @RequestMapping("/view")
    public String showDocuments(Model model) {
        List<Loan> documents = loanService.getAllLoans();
        model.addAttribute("documents", documents);
        return "viewAllDocs";
    }
}
