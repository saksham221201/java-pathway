package com.nagarro.transactionmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private String cardNumber;
    private String email;
    private String cvv;
    private String accountNumber;
    private CardType cardType;
}
