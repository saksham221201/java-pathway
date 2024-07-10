package com.nagarro.cardmodule.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordAlreadyExistsException extends RuntimeException{
    private String error;
    private int code;
}
