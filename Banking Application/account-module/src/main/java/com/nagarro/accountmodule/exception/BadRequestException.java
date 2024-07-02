package com.nagarro.accountmodule.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    private String error;
    private int code;

    public BadRequestException(String error, int code) {
        this.error = error;
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
