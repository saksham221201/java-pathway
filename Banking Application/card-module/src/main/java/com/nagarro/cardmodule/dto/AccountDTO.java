package com.nagarro.cardmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long accountId;
    private int accountNumber;
    private Long userId;
    private String email;
    private String accountType;
    private double balance;
}