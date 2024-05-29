package com.nagarro.usermodule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nagarro.usermodule.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		ErrorResponse response = new ErrorResponse("Error occured " + e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRecordNotFoundException(Exception e) {
		ErrorResponse response = new ErrorResponse("Record Not Found: " + e.getMessage(),
				HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RecordAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleRecordAlreadyExistsException(Exception e) {
		ErrorResponse response = new ErrorResponse("" + e.getMessage(),
				HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(Exception e) {
		ErrorResponse response = new ErrorResponse("Not Authorized to access " + e.getMessage(),
				HttpStatus.UNAUTHORIZED.value());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
}
