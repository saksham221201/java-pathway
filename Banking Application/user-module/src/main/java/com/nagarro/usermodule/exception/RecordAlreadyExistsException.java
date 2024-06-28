package com.nagarro.usermodule.exception;

import lombok.Getter;

@Getter
public class RecordAlreadyExistsException extends RuntimeException{
	private String error;
	private int code;

	public RecordAlreadyExistsException(String error, int code) {
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

