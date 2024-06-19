package com.nagarro.usermodule.exception;

import lombok.Getter;

@Getter
public class UnauthorizedAccessException extends RuntimeException{
	private String error;
	private int code;

	public UnauthorizedAccessException(String error, int code) {
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
