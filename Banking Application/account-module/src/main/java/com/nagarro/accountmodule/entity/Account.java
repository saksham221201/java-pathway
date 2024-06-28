package com.nagarro.accountmodule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    @GeneratedValue(generator = "UUID")
    @Id
    private Long accountNumber;
    private String name;
    private String email;
    private String mobile;
    @Enumerated(EnumType.STRING)
    private String accountType;
    private double balance;
}
