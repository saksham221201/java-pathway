package com.nagarro.loanmodule.controller;

import com.nagarro.loanmodule.entity.Loan;
import com.nagarro.loanmodule.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("message", "Welcome to our website!");
        return "index";
    }

    @PostMapping("/apply")
    public ResponseEntity<Loan> applyLoan(@RequestBody Loan loan) {
        Loan appliedLoan = loanService.applyLoan(loan);
        return new ResponseEntity<>(appliedLoan, HttpStatus.CREATED);
    }
}
