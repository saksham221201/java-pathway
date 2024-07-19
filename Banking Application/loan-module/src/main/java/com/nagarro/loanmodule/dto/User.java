package com.nagarro.loanmodule.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Data
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
