package com.nagarro.accountmodule.dto;

import jakarta.persistence.*;
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
