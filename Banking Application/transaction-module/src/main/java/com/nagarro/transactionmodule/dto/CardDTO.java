package com.nagarro.transactionmodule.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private String cardNumber;
    private String name;
    private String email;
    private String cvv;
    private Long expenseLimit;
    private String accountNumber;
    private boolean activationStatus;
    private LocalDate expiryDate;

    private CardType cardType;

    private Long dailyLimit;
    private boolean lost;
}
