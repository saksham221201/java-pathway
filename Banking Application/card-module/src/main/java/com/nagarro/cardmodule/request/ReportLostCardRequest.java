package com.nagarro.cardmodule.request;

import lombok.Data;

@Data
public class ReportLostCardRequest {
    private Long cardNumber;
    private String email;
    private String accountNumber;
}
