package com.nagarro.cardmodule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String cvv;
    private Long expenseLimit;
    private int accountNo;
    private boolean activationStatus;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private Long dailyLimit;
    private boolean lost;
}
