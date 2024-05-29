package com.nagarro.usermodule.exception;

public class RecordAlreadyExistsException extends RuntimeException{
	private String message;
	private Object fieldValue;

	public RecordAlreadyExistsException(String message, Long fieldValue) {
		super(message + " - " + fieldValue);
		this.message = message;
		this.fieldValue = fieldValue;
	}
	public RecordAlreadyExistsException(String message, Integer fieldValue) {
		super(message + " - " + fieldValue);
		this.message = message;
		this.fieldValue = fieldValue;
	}

	public RecordAlreadyExistsException(String message, String fieldValue) {
		super(message + " - " + fieldValue);
		this.message = message;
		this.fieldValue = fieldValue;
	}

	public String getMessage() {
		return message;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}

