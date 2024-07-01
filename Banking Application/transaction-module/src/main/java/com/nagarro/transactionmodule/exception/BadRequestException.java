package com.nagarro.transactionmodule.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BadRequestException extends RuntimeException{
    private String error;
    private int code;
}
