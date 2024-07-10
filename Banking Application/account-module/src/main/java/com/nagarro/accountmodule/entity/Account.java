package com.nagarro.accountmodule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator",strategy = "com.nagarro.accountmodule.entity.CustomIdGenerator")
    private String accountNumber;
    private Long userId;
    private String email;
    private String accountType;
    private double balance;
}
