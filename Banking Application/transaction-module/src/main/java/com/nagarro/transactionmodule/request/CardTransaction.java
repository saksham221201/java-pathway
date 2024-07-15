package com.nagarro.transactionmodule.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardTransaction {
    private String cardNumber;
    private String email;
    private String cvv;
    private String accountNumber;
    private double amount;
}
