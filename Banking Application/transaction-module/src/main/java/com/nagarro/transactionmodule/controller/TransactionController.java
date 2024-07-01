package com.nagarro.transactionmodule.controller;

import com.nagarro.transactionmodule.dto.AccountDTO;
import com.nagarro.transactionmodule.request.MoneyRequest;
import com.nagarro.transactionmodule.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<AccountDTO> addMoneyToAccount(@RequestBody MoneyRequest moneyRequest){
        AccountDTO updatedAccount = transactionService.depositMoney(moneyRequest);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDTO> withdrawMoneyToAccount(@RequestBody MoneyRequest moneyRequest){
        AccountDTO updatedAccount = transactionService.withdrawMoney(moneyRequest);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
}
