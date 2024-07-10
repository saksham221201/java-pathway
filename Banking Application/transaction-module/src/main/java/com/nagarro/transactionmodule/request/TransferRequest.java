package com.nagarro.transactionmodule.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private String debitAccountNumber;
    private String creditAccountNumber;
    private String email;
    private double amount;
}
