package com.nagarro.accountmodule.exception;

import com.nagarro.accountmodule.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> duplicateEntryExceptionHandler(RecordAlreadyExistsException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getError(), e.getCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<ErrorResponse> emptyInputExceptionHandler(EmptyInputException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getError(), e.getCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(RecordNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getError(), e.getCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
