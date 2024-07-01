package com.nagarro.transactionmodule.dto;

import com.nagarro.transactionmodule.constant.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long accountNumber;
    private String email;
    private AccountType accountType;
    private double balance;
}
