package com.nagarro.transactionmodule.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private int accountNumber;
    private String email;
    private double amount;
}
