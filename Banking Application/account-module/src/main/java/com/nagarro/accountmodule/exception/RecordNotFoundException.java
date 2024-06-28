package com.nagarro.accountmodule.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordNotFoundException extends RuntimeException{
    private String error;
    private int code;
}
