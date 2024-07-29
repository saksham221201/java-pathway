package com.nagarro.kycmodule.exception;

import com.nagarro.kycmodule.response.ErrorResponse;
import feign.FeignException;
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

    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getError(), e.getCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FeignException.FeignClientException.class})
    public ResponseEntity<ErrorResponse> feignClientExceptionHandler(Exception e){
        ErrorResponse errorResponse = new ErrorResponse("User Not found with userId!!", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(BadRequestException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getError(), e.getCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
