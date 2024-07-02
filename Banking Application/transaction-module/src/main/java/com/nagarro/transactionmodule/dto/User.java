package com.nagarro.transactionmodule.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile;

    @Enumerated(EnumType.STRING)
    private Role role;
}
