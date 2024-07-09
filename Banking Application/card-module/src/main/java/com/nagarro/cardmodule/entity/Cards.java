package com.nagarro.cardmodule.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cards {

    private String id;
    private String name;
    private int cvv;
    private Long limit;
    private int accountNo;
    private boolean status;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private Long dailyLimit;
    private boolean lost;
}
