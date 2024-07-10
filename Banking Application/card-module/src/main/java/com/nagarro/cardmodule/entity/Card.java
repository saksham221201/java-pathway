package com.nagarro.cardmodule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator",strategy = "com.nagarro.cardmodule.entity.CustomIdGenerator")
    private String id;
    private String name;
    private String email;
    private String cvv;
    private Long expenseLimit;
    private int accountNumber;
    private boolean activationStatus;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private Long dailyLimit;
    private boolean lost;
}
