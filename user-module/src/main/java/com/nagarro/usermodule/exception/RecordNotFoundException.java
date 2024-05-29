package com.nagarro.usermodule.exception;

public class RecordNotFoundException extends RuntimeException {
	private String exceptionDetail;
	private Object fieldValue;

	public RecordNotFoundException(String exceptionDetail, Long fieldValue) {
		super(exceptionDetail + " - " + fieldValue);
		this.exceptionDetail = exceptionDetail;
		this.fieldValue = fieldValue;
	}

	public RecordNotFoundException(String exceptionDetail, String fieldValue) {
		super(exceptionDetail + " - " + fieldValue);
		this.exceptionDetail = exceptionDetail;
		this.fieldValue = fieldValue;
	}

	public String getExceptionDetail() {
		return exceptionDetail;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}

