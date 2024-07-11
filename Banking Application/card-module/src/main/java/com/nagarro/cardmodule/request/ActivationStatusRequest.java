package com.nagarro.cardmodule.request;

import lombok.Data;

@Data
public class ActivationStatusRequest {
    private Long cardNumber;
    private String email;
    private String accountNumber;
    private boolean activationStatus;
}
