package com.nagarro.transactionmodule.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MailException extends RuntimeException {
    private String error;
    private int code;
}
