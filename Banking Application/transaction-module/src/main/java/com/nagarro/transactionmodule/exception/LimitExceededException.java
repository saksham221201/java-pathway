package com.nagarro.transactionmodule.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimitExceededException extends RuntimeException {
    private String error;
    private int code;
}
