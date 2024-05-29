package com.nagarro.usermodule.exception;

public class UnauthorizedAccessException extends RuntimeException{

	public UnauthorizedAccessException(String message) {
		super(message);		
	}
}
