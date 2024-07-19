package com.nagarro.loanmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String accountNumber;
    private Long userId;
    private String email;
    private String accountType;
    private double balance;
}