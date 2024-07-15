package com.nagarro.cardmodule.request;

import lombok.Data;

@Data
public class ReportLostCardRequest {
    private String cardNumber;
    private String email;
    private String accountNumber;
}
