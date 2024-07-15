package com.nagarro.transactionmodule.controller;

import com.lowagie.text.DocumentException;
import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.entity.Transaction;
import com.nagarro.transactionmodule.exporter.PDFExporter;
import com.nagarro.transactionmodule.request.CardTransaction;
import com.nagarro.transactionmodule.request.TransactionRequest;
import com.nagarro.transactionmodule.request.TransferRequest;
import com.nagarro.transactionmodule.service.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<AccountDTO> addMoneyToAccount(@RequestBody TransactionRequest moneyRequest){
        AccountDTO updatedAccount = transactionService.depositMoney(moneyRequest);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDTO> withdrawMoneyToAccount(@RequestBody TransactionRequest moneyRequest){
        AccountDTO updatedAccount = transactionService.withdrawMoney(moneyRequest);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PostMapping("/card/withdraw")
    public ResponseEntity<AccountDTO> withdrawMoneyWithCard(@RequestBody CardTransaction cardTransaction){
        AccountDTO updatedAccount = transactionService.cardWithdrawal(cardTransaction);
        return new ResponseEntity<>(updatedAccount,HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<AccountDTO> transferMoney(@RequestBody TransferRequest transferRequest){
        AccountDTO transferMoney = transactionService.transferMoney(transferRequest);
        return new ResponseEntity<>(transferMoney, HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountNumber(@PathVariable String accountNumber) {
        List<Transaction> transactions = transactionService.getTransactions(accountNumber);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }


    @GetMapping("/export/pdf/{accountNumber}")
    public void exportToPDF(HttpServletResponse response, @PathVariable String accountNumber) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        LocalDateTime currentDateTime = LocalDateTime.now();

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Transaction> transactions = transactionService.getTransactions(accountNumber);

        PDFExporter exporter = new PDFExporter(transactions);
        exporter.export(response);

    }
}
