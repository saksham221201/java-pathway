package com.nagarro.cardmodule.request;

import lombok.Data;

@Data
public class ActivationStatusRequest {
    private Long id;
    private String email;
    private int accountNumber;
    private boolean activationStatus;
}
