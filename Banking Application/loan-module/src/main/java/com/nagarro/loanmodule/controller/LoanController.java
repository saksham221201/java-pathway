package com.nagarro.loanmodule.controller;

import com.nagarro.loanmodule.entity.Loan;
import com.nagarro.loanmodule.request.LoginRequest;
import com.nagarro.loanmodule.request.VerifyStatusRequest;
import com.nagarro.loanmodule.response.LoginResponse;
import com.nagarro.loanmodule.service.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Controller
public class LoanController {

    private final Logger logger = LoggerFactory.getLogger(LoanController.class);

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/")
    public String home(Model model) {
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
    public ResponseEntity<Loan> verifyLoan(@PathVariable String loanId){
        Loan loan = loanService.verifyLoan(loanId);

        return new ResponseEntity<>(loan,HttpStatus.OK);
    }

    @PostMapping("/loanStatus")
    public ResponseEntity<Loan> loanStatus(@RequestBody VerifyStatusRequest verifyStatusRequest){
        Loan loan = loanService.changeLoanStatus(verifyStatusRequest);

        return new ResponseEntity<>(loan,HttpStatus.OK);
    }

    @RequestMapping("/view")
    public String showDocuments(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        List<Loan> documents = loanService.getAllLoans();
        model.addAttribute("documents", documents);
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        LoginRequest loginRequest = LoginRequest
                .builder()
                .email(email)
                .password(password)
                .build();
        boolean loginResponse = loanService.login(loginRequest);
        logger.info("LoginResponse: {}", loginResponse);
        if (loginResponse) return "viewAllDocs";
        return "index";
    }

    @RequestMapping("/updateStatus")
    public String updateVerificationStatus(@RequestParam String loanId, @RequestParam boolean verified, @RequestParam String email, @RequestParam String password, Model model) {
        logger.info("Update Verification Status Hit in controller");
        Loan loan = loanService.verifyLoan(loanId);
        return showDocuments(email, password, model);
    }

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("contextPath", request.getContextPath());
    }

}
